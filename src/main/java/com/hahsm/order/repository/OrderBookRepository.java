package com.hahsm.order.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import com.hahsm.common.type.Repository;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.type.Pair;
import com.hahsm.datastructure.adt.List;
import com.hahsm.order.model.OrderBook;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;

public class OrderBookRepository implements Repository<OrderBook, Pair<Integer, Integer>> {
    private final DatabaseConnectionManager connectionManager;
    private final HashMap<Pair<Integer, Integer>, OrderBook> orderBooks;

    public OrderBookRepository(final DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        orderBooks = new HashMap<>();
        load();
    }


    @Override
    public List<OrderBook> getAll() {
        assert orderBooks != null;
        return orderBooks.values();
    }

    @Override
    public Optional<OrderBook> getByID(final Pair<Integer, Integer> id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByID'");
    }

    @Override
    public boolean update(OrderBook entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public OrderBook insert(OrderBook newEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean delete(OrderBook entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean deleteByID(Pair<Integer, Integer> id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByID'");
    }

    @Override
    public List<OrderBook> insert(List<OrderBook> entities) {
        final String sql = "INSERT INTO " + DatabaseConstants.ORDER_BOOK_TABLE + '(' +
                DatabaseConstants.OrderBookColumns.ORDER_ID + ',' +
                DatabaseConstants.OrderBookColumns.BOOK_ID + ',' +
                DatabaseConstants.OrderBookColumns.QUANTITY +
                ") VALUES(?,?,?);";

        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (int i = 0; i < entities.size(); ++i) {
                final OrderBook ob = entities.get(i);
                pstmt.setInt(1, ob.getOrderId());
                pstmt.setInt(2, ob.getBookId());
                pstmt.setInt(3, ob.getQuantity());

                pstmt.addBatch();

                if (i % 1000 == 0 || i == entities.size() - 1) {
                    pstmt.executeBatch();
                }
            }

            conn.commit();
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
        assert orderBooks != null;
        orderBooks.clear();

        final String sql = "SELECT " +
                DatabaseConstants.OrderBookColumns.ORDER_ID + ',' +
                DatabaseConstants.OrderBookColumns.BOOK_ID + ',' +
                DatabaseConstants.OrderBookColumns.QUANTITY +
                " FROM " + DatabaseConstants.ORDER_BOOK_TABLE;

        final List<OrderBook> queryResult = new ArrayList<OrderBook>();

        try (var conn = connectionManager.getConnection()) {
            final var stmt = conn.createStatement();
            final var result = stmt.executeQuery(sql);
            
            new OrderBook();

            while (result.next()) {
                queryResult.add(new OrderBook(
                        result.getInt(DatabaseConstants.OrderBookColumns.ORDER_ID),
                        result.getInt(DatabaseConstants.OrderBookColumns.BOOK_ID),
                        result.getInt(DatabaseConstants.OrderBookColumns.QUANTITY)));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        for (int i = 0; i < queryResult.size(); ++i) {
            final OrderBook orderBook = queryResult.get(i);
            orderBooks.put(orderBook.getId(), orderBook);
        }
    }
}
