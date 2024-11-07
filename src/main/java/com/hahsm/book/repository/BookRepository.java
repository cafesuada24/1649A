package com.hahsm.book.repository;


import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

import com.hahsm.datastructure.HashMap;
import com.hahsm.datastructure.adt.List;

import de.vandermeer.asciitable.AsciiTable;

import com.hahsm.book.model.Book;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Repository;
import com.hahsm.database.DatabaseConnectionManager;

public class BookRepository implements Repository<Book, Integer> {
    private final DatabaseConnectionManager connectionManager;
    private HashMap<Integer, Book> books = new HashMap<>();

    public BookRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        load();
    }

    @Override
    public List<Book> getAll() {
        assert books != null;
        return books.values();
    }

    @Override
    public Optional<Book> getByID(Integer id) {
        assert books != null;

        if (!books.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(books.get(id));
    }

    @Override
    public boolean update(Book entity) {
        assert books != null;

        String sql = "UPDATE " + DatabaseConstants.BOOK_TABLE + " SET " +
                DatabaseConstants.BookColumns.TITLE + " = ?," +
                DatabaseConstants.BookColumns.AUTHOR + " = ?," +
                " WHERE " + DatabaseConstants.BookColumns.ID + " = ?;";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getAuthor());
            pstmt.setInt(3, entity.getID());

            int updatedRows = pstmt.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public boolean delete(Book entity) {
        assert books != null;

        return deleteByID(entity.getId());
    }

    @Override
    public boolean deleteByID(Integer id) {
        assert books != null;

        String sql = "DELETE FROM " + DatabaseConstants.BOOK_TABLE
                + " WHERE " + DatabaseConstants.BookColumns.ID + " = ?";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            books.remove(id);

            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return false;
    }

    @Override
    public Book insert(Book newEntity) {
        assert books != null;

        String sql = "INSERT INTO " + DatabaseConstants.BOOK_TABLE + '(' +
                DatabaseConstants.BookColumns.TITLE + ',' +
                DatabaseConstants.BookColumns.AUTHOR + ')' +
                " VALUES(?,?);";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEntity.getTitle());
            pstmt.setString(2, newEntity.getAuthor());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Unable to insert book");
            }

            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    final int generatedId = generatedKeys.getInt(1);
                    newEntity.setId(generatedId);
                    books.put(generatedId, newEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }
        return newEntity;
    }

    private void load() {
        assert books != null;
        books.clear();

        String sql = "SELECT " +
                DatabaseConstants.BookColumns.ID + ',' +
                DatabaseConstants.BookColumns.TITLE + ',' +
                DatabaseConstants.BookColumns.AUTHOR + ',' +
                DatabaseConstants.BookColumns.YEAR +
                " FROM " + DatabaseConstants.BOOK_TABLE;

        try (var conn = connectionManager.getConnection()) {
            var stmt = conn.createStatement();
            var result = stmt.executeQuery(sql);

            while (result.next()) {
                final Book book = new Book(
                        result.getInt(DatabaseConstants.BookColumns.ID),
                        result.getString(DatabaseConstants.BookColumns.TITLE),
                        result.getString(DatabaseConstants.BookColumns.AUTHOR),
                        result.getInt(DatabaseConstants.BookColumns.YEAR));
                books.put(book.getId(), book);            
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public List<Book> insert(List<Book> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

	@Override
	public List<Book> getByFilter(Predicate<Book> filter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getByFilter'");
	}

    @Override
    public String toString() {
        AsciiTable tb = new AsciiTable();

        tb.addRule();
        tb.addRow(
                DatabaseConstants.BookColumns.ID,
                DatabaseConstants.BookColumns.TITLE,
                DatabaseConstants.BookColumns.AUTHOR,
                DatabaseConstants.BookColumns.YEAR);
        tb.addRule();

        for (final var book : books.values()) {
            tb.addRow(book.getID(), book.getTitle(), book.getAuthor(), book.getYear());
        }
        tb.addRule();
        return tb.render();
    }

	@Override
	public void registerObserver(Observer<Book> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registerObserver'");
	}

	@Override
	public void removeObserver(Observer<Book> observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeObserver'");
	}

	@Override
	public void notifyObservers(Book data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notifyObservers'");
	}
}
