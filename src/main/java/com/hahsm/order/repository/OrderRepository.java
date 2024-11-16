package com.hahsm.order.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.Predicate;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;
import com.hahsm.book.model.Book;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.type.Observable;
import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Repository;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.order.model.Order;

public class OrderRepository implements Repository<Order, Integer> {
    /**
     *
     */
    private final DatabaseConnectionManager connectionManager;
    // private final Repository<Book, Integer> bookRepository;
    private final OrderBookRepository orderBookRepository;
    private final HashMap<Integer, Order> orders;
    private final List<Observer<Order>> observers = new ArrayList<>();

    public OrderRepository(final DatabaseConnectionManager connectionManager,
            final Repository<Book, Integer> bookRepository) {
        // this.bookRepository = bookRepository;
        this.orderBookRepository = new OrderBookRepository(connectionManager, bookRepository);
        this.connectionManager = connectionManager;
        orders = new HashMap<>();
        load();
    }

    @Override
    public List<Order> getAll() {
        assert orders != null;
        return orders.values();
    }

    @Override
    public List<Order> getByFilter(Predicate<Order> filter) {
        return orders.values().filter(filter);
    }

    @Override
    public boolean update(final Order updatedEntity) {
        final String sql = "UPDATE " + DatabaseConstants.ORDER_TABLE + " SET " +
                DatabaseConstants.OrderColumns.ORDER_TIME + " = ?, " +
                DatabaseConstants.OrderColumns.ESTIMATED_DELIVERY_TIME + " = ?, " +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + " = ?, " +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + " = ?, " +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE + " = ?, " +
                DatabaseConstants.OrderColumns.STATUS + " = ? " +
                "WHERE " + DatabaseConstants.OrderColumns.ID + " = ?;";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            // Set parameters for update
            pstmt.setLong(1, updatedEntity.getOrderTime().toEpochSecond(ZoneOffset.UTC));
            System.out.println(updatedEntity.getOrderTime());
            System.out.println(updatedEntity.getOrderTime().toEpochSecond(ZoneOffset.UTC));
            if (updatedEntity.getEstimatedDeliveryTime() != null) {
                pstmt.setLong(2, updatedEntity.getEstimatedDeliveryTime().toEpochSecond(ZoneOffset.UTC));
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setString(3, updatedEntity.getCustomerName());
            pstmt.setString(4, updatedEntity.getCustomerAddress());
            pstmt.setString(5, updatedEntity.getCustomerPhone());
            pstmt.setString(6, updatedEntity.getStatus().name());
            pstmt.setInt(7, updatedEntity.getId());

            final int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        orders.put(updatedEntity.getId(), updatedEntity);

        return true;
    }

    @Override
    public boolean delete(final Order entity) {
        return deleteByID(entity.getId());
    }

    @Override
    public boolean deleteByID(final Integer id) {
        assert orders != null;

        final String sql = "DELETE FROM " + DatabaseConstants.ORDER_TABLE +
                " WHERE " + DatabaseConstants.OrderColumns.ID + " = ?";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            final int rowsDeleted = pstmt.executeUpdate();

            assert rowsDeleted == 0 || rowsDeleted == 1;

            orders.remove(id);

            return rowsDeleted == 1;
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return false;
    }

    @Override
    public Order insert(final Order newEntity) {
        final String sql = "INSERT INTO " + DatabaseConstants.ORDER_TABLE + '(' +
                DatabaseConstants.OrderColumns.ORDER_TIME + ',' +
                DatabaseConstants.OrderColumns.ESTIMATED_DELIVERY_TIME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE + ',' +
                DatabaseConstants.OrderColumns.STATUS +
                ") VALUES(?,?,?,?,?,?);";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, newEntity.getOrderTime().toEpochSecond(ZoneOffset.UTC));
            if (newEntity.getEstimatedDeliveryTime() != null) {
                pstmt.setLong(2, newEntity.getEstimatedDeliveryTime().toEpochSecond(ZoneOffset.UTC));
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setString(3, newEntity.getCustomerName());
            pstmt.setString(4, newEntity.getCustomerAddress());
            pstmt.setString(5, newEntity.getCustomerPhone());
            pstmt.setString(6, newEntity.getStatus().name());

            final int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Unable to insert user");
            }

            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    final int id = generatedKeys.getInt(1);
                    newEntity.setId(id);
                }
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        assert newEntity.getOrderBooks() != null;
        assert newEntity.getId() != 0;

        orders.put(newEntity.getId(), newEntity);

        final var orderBooks = newEntity.getOrderBooks();
        final int id = newEntity.getId();

        for (int i = 0; i < orderBooks.size(); ++i) {
            orderBooks.get(i).setOrderId(id);
        }

        orderBookRepository.insert(newEntity.getOrderBooks());
        notifyObservers(newEntity);

        return newEntity;
    }

    @Override
    public Optional<Order> getByID(Integer id) {
        assert orders != null;

        if (!orders.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(orders.get(id));
    }

    @Override
    public List<Order> insert(final List<Order> entities) {
        assert orders != null;

        final String sql = "INSERT INTO " + DatabaseConstants.ORDER_TABLE + '(' +
                DatabaseConstants.OrderColumns.ORDER_TIME + ',' +
                DatabaseConstants.OrderColumns.ESTIMATED_DELIVERY_TIME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE + ',' +
                DatabaseConstants.OrderColumns.STATUS +
                ") VALUES(?,?,?,?,?,?);";

        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (int i = 0; i < entities.size(); ++i) {
                final Order order = entities.get(i);
                pstmt.setLong(1, order.getOrderTime().toEpochSecond(ZoneOffset.UTC));
                if (order.getEstimatedDeliveryTime() != null) {
                    pstmt.setLong(2, order.getEstimatedDeliveryTime().toEpochSecond(ZoneOffset.UTC));
                } else {
                    pstmt.setNull(2, Types.INTEGER);
                }
                pstmt.setString(3, order.getCustomerName());
                pstmt.setString(4, order.getCustomerAddress());
                pstmt.setString(5, order.getCustomerPhone());
                pstmt.setString(6, order.getStatus().name());

                pstmt.addBatch();

                if (i % 1000 == 0 || i == entities.size() - 1) {
                    pstmt.executeBatch();
                }
            }

            conn.commit();

            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                for (int index = 0; generatedKeys.next(); ++index) {
                    final int generatedId = generatedKeys.getInt(1);
                    entities.get(index).setId(generatedId);
                    orders.put(generatedId, entities.get(index));
                }
            }

            conn.setAutoCommit(true);
        } catch (final SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.println("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return entities;
    }

    private void load() {
        assert orders != null;
        orders.clear();

        final String sql = "SELECT " +
                DatabaseConstants.OrderColumns.ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME + ',' +
                DatabaseConstants.OrderColumns.ESTIMATED_DELIVERY_TIME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE + ',' +
                DatabaseConstants.OrderColumns.STATUS +
                " FROM " + DatabaseConstants.ORDER_TABLE;

        try (var conn = connectionManager.getConnection()) {
            final var stmt = conn.createStatement();
            final var result = stmt.executeQuery(sql);

            while (result.next()) {
                final Order order = new Order(
                        result.getInt(DatabaseConstants.OrderColumns.ID),
                        result.getLong(DatabaseConstants.OrderColumns.ORDER_TIME),
                        result.getLong(DatabaseConstants.OrderColumns.ESTIMATED_DELIVERY_TIME),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_NAME),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_PHONE),
                        result.getString(DatabaseConstants.OrderColumns.STATUS));
                final var orderBooks = orderBookRepository.getByFilter((x) -> x.getOrderId() == order.getId());
                order.setOrderBooks(orderBooks);
                orders.put(order.getId(), order);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void registerObserver(Observer<Order> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Order> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(final Order newOrder) {
        for (Observer<Order> observer : observers) {
            observer.update(newOrder);
        }
    }
}
