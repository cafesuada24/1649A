package com.hahsm.order.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

import com.hahsm.common.type.Repository;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.book.model.Book;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Pair;
import com.hahsm.datastructure.adt.List;
import com.hahsm.order.model.OrderBook;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;

public class OrderBookRepository implements Repository<OrderBook, Pair<Integer, Integer>> {
    private final DatabaseConnectionManager connectionManager;
    private final HashMap<Pair<Integer, Integer>, OrderBook> orderBooks;
    final Repository<Book, Integer> bookRepository;

    public OrderBookRepository(
            final DatabaseConnectionManager connectionManager,
            final Repository<Book, Integer> bookRepository) {
        this.connectionManager = connectionManager;
        orderBooks = new HashMap<>();
        this.bookRepository = bookRepository;
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
	public List<OrderBook> getByFilter(Predicate<OrderBook> filter) {
        final List<OrderBook> matches = new ArrayList<>();

        for (final var ob: orderBooks.values()) {
            if (filter.test(ob)) {
                matches.add(ob);
            }
        }

        return matches;
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

        try (var conn = connectionManager.getConnection()) {
            final var stmt = conn.createStatement();
            final var result = stmt.executeQuery(sql);
            
            while (result.next()) {
                final var ob = new OrderBook(
                        result.getInt(DatabaseConstants.OrderBookColumns.ORDER_ID),
                        result.getInt(DatabaseConstants.OrderBookColumns.BOOK_ID),
                        result.getInt(DatabaseConstants.OrderBookColumns.QUANTITY));
                final var book = bookRepository.getByID(ob.getBookId());
                if (book.isPresent()) {
                    ob.setBook(book.get());  
                }
                orderBooks.put(ob.getId(), ob);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }
    }


	@Override
	public void registerObserver(Observer<OrderBook> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registerObserver'");
	}


	@Override
	public void removeObserver(Observer<OrderBook> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeObserver'");
	}


	@Override
	public void notifyObservers(OrderBook data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notifyObservers'");
	}

}
