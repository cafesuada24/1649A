package com.hahsm.order.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;
import com.hahsm.algorithm.Search;
import com.hahsm.book.model.Book;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.type.Repository;
import com.hahsm.common.type.Pair;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;

public class OrderRepository implements Repository<Order, Integer> {
    /**
     *
     */
    private final DatabaseConnectionManager connectionManager;
    // private final Repository<Book, Integer> bookRepository;
    private final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepository;
    private final HashMap<Integer, Order> orders;

    public OrderRepository(
            final DatabaseConnectionManager connectionManager,
            final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepository) {
        this.connectionManager = connectionManager;
        this.orderBookRepository = orderBookRepository;
        orders = new HashMap<>();
        load();
    }

    @Override
    public List<Order> getAll() {
        assert orders != null;
        return orders.values();
    }

    @Override
    public boolean update(final Order entity) {
        return false;
    }

    @Override
    public boolean delete(final Order entity) {
        return deleteByID(entity.getId());
    }

    @Override
    public boolean deleteByID(final Integer id) {
        assert orders != null;

        final String sql = "DELETE FROM " + DatabaseConstants.USER_TABLE +
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
        // DatabaseConstants.OrderColumns.ID + ',' +
                DatabaseConstants.OrderColumns.USER_ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_DATE + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE +
                ") VALUES(?,?,?,?,?,?);";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newEntity.getUserId());
            pstmt.setDate(2, newEntity.getOrderDate());
            pstmt.setTime(3, newEntity.getOrderTime());
            pstmt.setString(4, newEntity.getCustomerName());
            pstmt.setString(5, newEntity.getCustomerAddress());
            pstmt.setString(6, newEntity.getCustomerPhone());

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
    public List<Order> insert(List<Order> entities) {
        assert orders != null;

        final String sql = "INSERT INTO " + DatabaseConstants.USER_TABLE + '(' +
                DatabaseConstants.OrderColumns.USER_ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_DATE + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME +
                ") VALUES(?,?,?);";

        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (int i = 0; i < entities.size(); ++i) {
                final Order order = entities.get(i);
                pstmt.setInt(1, order.getUserId());
                pstmt.setDate(2, order.getOrderDate());
                pstmt.setTime(3, order.getOrderTime());

                pstmt.addBatch();

                if (i % 1000 == 0 || i == entities.size() - 1) {
                    pstmt.executeBatch();
                }
            }

            conn.commit();

            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                int index = 0;
                while (generatedKeys.next()) {
                    entities.get(index).setId(generatedKeys.getInt(1));
                }
                for (int i = 0; i < entities.size(); ++i) {
                    assert entities.get(i).getId() != 0;

                }
            }

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
                DatabaseConstants.OrderColumns.USER_ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_DATE + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_NAME + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS + ',' +
                DatabaseConstants.OrderColumns.CUSTOMER_PHONE +
                " FROM " + DatabaseConstants.ORDER_TABLE;

        final List<Order> queryResult = new ArrayList<Order>();

        try (var conn = connectionManager.getConnection()) {
            final var stmt = conn.createStatement();
            final var result = stmt.executeQuery(sql);

            while (result.next()) {
                queryResult.add(new Order(
                        result.getInt(DatabaseConstants.OrderColumns.ID),
                        result.getInt(DatabaseConstants.OrderColumns.USER_ID),
                        result.getDate(DatabaseConstants.OrderColumns.ORDER_DATE),
                        result.getTime(DatabaseConstants.OrderColumns.ORDER_TIME),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_NAME),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_ADDRESS),
                        result.getString(DatabaseConstants.OrderColumns.CUSTOMER_PHONE)));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        for (int i = 0; i < queryResult.size(); ++i) {
            final Order order = queryResult.get(i);
            orders.put(order.getId(), order);
        }
    }
}
