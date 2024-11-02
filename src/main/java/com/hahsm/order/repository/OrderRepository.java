package com.hahsm.order.repository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;
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
    private final Repository<Book, Integer> bookRepository;
    private final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepository;
    private Search searcher;
    private List<Order> orders;

    public OrderRepository(
            final DatabaseConnectionManager connectionManager,
            final Repository<Book, Integer> bookRepository,
            final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepository) {
        this.connectionManager = connectionManager;
        this.bookRepository = bookRepository;
        this.orderBookRepository = orderBookRepository;
    }

    @Override
    public List<Order> getAll() {
        final String sql = "SELECT " +
                DatabaseConstants.OrderColumns.ID + ',' +
                DatabaseConstants.OrderColumns.USER_ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_DATE + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME +
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
                        result.getTime(DatabaseConstants.OrderColumns.ORDER_TIME)));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return queryResult;
    }

    public void setSearchAlgorithm(Search searcher) {
        this.searcher = searcher;
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
        final String sql = "DELETE FROM " + DatabaseConstants.USER_TABLE +
                " WHERE " + DatabaseConstants.OrderColumns.ID + " = ?";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            final int rowsDeleted = pstmt.executeUpdate();

            assert rowsDeleted == 0 || rowsDeleted == 1;

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
        final String sql = "INSERT INTO " + DatabaseConstants.USER_TABLE + '(' +
                DatabaseConstants.OrderColumns.USER_ID + ',' +
                DatabaseConstants.OrderColumns.ORDER_DATE + ',' +
                DatabaseConstants.OrderColumns.ORDER_TIME +
                ") VALUES(?,?,?);";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newEntity.getUserId());
            pstmt.setDate(2, newEntity.getOrderDate());
            pstmt.setTime(3, newEntity.getOrderTime());

            final int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Unable to insert user");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }
        return newEntity;
    }

	@Override
	public void loadEntity(final Order entity) {
        //if (searcher == null) {
        //    throw new IllegalStateException("Search algorithm is not set");
        //}
        //
        //final List<OrderBook> orderBooks = orderBookRepository.getAll();
        //final List<Book> books = bookRepository.getAll();
        //
        //List<Integer> indexes = searcher.searchAll(orderBooks, (orderBook) -> {
        //    return orderBook.getOrderId() == entity.getId() ? 0 : 1;
        //});
        //
        //entity.setBooks(books);
	}

	@Override
	public Optional<Order> getByID(Integer id) {
        //if (orders == null) {
        //    orders = getAll();
        //}
        assert orders != null;
        assert searcher != null;

        final Function<Order, Integer> finder = order -> order.getId() == id ? 0 : 1;
        final int index = searcher.search(this.orders, finder);
        
        if (index == -1) {
            return Optional.empty();
        }

        return Optional.of(orders.get(index));
	}
}
