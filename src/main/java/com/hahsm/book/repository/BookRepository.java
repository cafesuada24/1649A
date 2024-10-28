package com.hahsm.book.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hahsm.book.model.Book;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.repository.Repository;
import com.hahsm.database.DatabaseConnectionManager;

public class BookRepository implements Repository<Book, Integer> {
    private final DatabaseConnectionManager connectionManager;

    public BookRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

	@Override
	public List<Book> getAll() {
        String sql = "SELECT " +
            DatabaseConstants.BookColumns.ID + ',' +
            DatabaseConstants.BookColumns.TITLE + ',' +
            DatabaseConstants.BookColumns.AUTHOR +
            " FROM " + DatabaseConstants.BOOK_TABLE;

        java.util.List<Book> queryResult = new ArrayList<Book>(); 

        try (var conn = connectionManager.getConnection()) {
            var stmt = conn.createStatement();           
            var result = stmt.executeQuery(sql);
            
            while (result.next()) {
                queryResult.add(new Book(
                    result.getInt(DatabaseConstants.BookColumns.ID),
                    result.getString(DatabaseConstants.BookColumns.TITLE),
                    result.getString(DatabaseConstants.BookColumns.AUTHOR)
                )); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return queryResult;
	}

	@Override
	public Optional<Book> getByID(Integer id) {
        String sql = "SELECT " +
            DatabaseConstants.BookColumns.ID + ',' +
            DatabaseConstants.BookColumns.TITLE + ',' +
            DatabaseConstants.BookColumns.AUTHOR +
            " FROM " + DatabaseConstants.BOOK_TABLE +
            " WHERE " + DatabaseConstants.BookColumns.ID + " = ?";


        try (var conn = connectionManager.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);

            var result = pstmt.executeQuery();
            if (result.next()) {
                return Optional.of(new Book(
                    result.getInt(DatabaseConstants.BookColumns.ID),
                    result.getString(DatabaseConstants.BookColumns.TITLE),
                    result.getString(DatabaseConstants.BookColumns.AUTHOR)
                )); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return Optional.empty();

	}

	@Override
	public boolean update(Book entity) {
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
        return deleteByID(entity.getID());
	}

	@Override
	public boolean deleteByID(Integer id) {
        String sql = "DELETE FROM " + DatabaseConstants.BOOK_TABLE
            + " WHERE " + DatabaseConstants.BookColumns.ID + " = ?";

        try (var conn = connectionManager.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
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
}
