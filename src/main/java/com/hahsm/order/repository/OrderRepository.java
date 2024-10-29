package com.hahsm.order.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.repository.Repository;
import com.hahsm.common.type.Pair;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.order.model.Order;

public class OrderRepository implements Repository<Order, Pair<Integer, Integer>> {
    /**
     *
     */
    private final DatabaseConnectionManager connectionManager;

    public OrderRepository(final DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

	@Override
	public List<Order> getAll() {
        final String sql = "SELECT " +
            DatabaseConstants.OrderColumns.USER_ID + ',' + 
            DatabaseConstants.OrderColumns.BOOK_ID + ',' +
            DatabaseConstants.OrderColumns.QUANTITY + ',' +
            DatabaseConstants.OrderColumns.ORDER_DATE + ',' + 
            DatabaseConstants.OrderColumns.ORDER_TIME +
            " FROM " + DatabaseConstants.ORDER_TABLE;

        final List<Order> queryResult = new ArrayList<Order>(); 

        try (var conn = connectionManager.getConnection()) {
            final var stmt = conn.createStatement();           
            final var result = stmt.executeQuery(sql);
            
            while (result.next()) {
                queryResult.add(new Order(
                    result.getInt(DatabaseConstants.OrderColumns.USER_ID),
                    result.getInt(DatabaseConstants.OrderColumns.BOOK_ID),
                    result.getInt(DatabaseConstants.OrderColumns.QUANTITY),
                    result.getDate(DatabaseConstants.OrderColumns.ORDER_DATE),
                    result.getTime(DatabaseConstants.OrderColumns.ORDER_TIME)
                )); 
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return queryResult;
	}

	@Override
	public Optional<Order> getByID(final Pair<Integer, Integer> id) {
        final String sql = "SELECT " +
            DatabaseConstants.OrderColumns.USER_ID + ',' + 
            DatabaseConstants.OrderColumns.BOOK_ID + ',' +
            DatabaseConstants.OrderColumns.QUANTITY + ',' +
            DatabaseConstants.OrderColumns.ORDER_DATE + ',' + 
            DatabaseConstants.OrderColumns.ORDER_TIME +
            " FROM " + DatabaseConstants.ORDER_TABLE +
            " WHERE " + DatabaseConstants.OrderColumns.USER_ID + " = ?" +
            " AND " + DatabaseConstants.OrderColumns.BOOK_ID + " = ?";


        try (var conn = connectionManager.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id.getFirst());
            pstmt.setInt(2, id.getSecond());

            final var result = pstmt.executeQuery();
            if (result.next()) {
                return Optional.of(new Order(
                    result.getInt(DatabaseConstants.OrderColumns.USER_ID),
                    result.getInt(DatabaseConstants.OrderColumns.BOOK_ID),
                    result.getInt(DatabaseConstants.OrderColumns.QUANTITY),
                    result.getDate(DatabaseConstants.OrderColumns.ORDER_DATE),
                    result.getTime(DatabaseConstants.OrderColumns.ORDER_TIME)
                )); 
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectionManager.closeConnection();
        }

        return Optional.empty();
	}

	@Override
	public boolean update(final Order entity) {
        return false;
        //final String sql = "UPDATE " + DatabaseConstants.USER_TABLE + " SET " +
        //    DatabaseConstants.OrderColumns.NAME + " = ?," + 
        //    " WHERE " + DatabaseConstants.OrderColumns.ID + " = ?;";
        //
        //try (var conn = connectionManager.getConnection();
        //        var pstmt = conn.prepareStatement(sql)) {
        //    pstmt.setString(1, entity.getName());
        //    pstmt.setString(2, entity.getAddress());
        //    pstmt.setInt(3, entity.getID()); 
        //
        //    final int updatedRows = pstmt.executeUpdate();
        //    return updatedRows == 1;
        //} catch (final SQLException e) {
        //    e.printStackTrace();
        //    return false;
        //} finally {
        //    connectionManager.closeConnection();
        //}
	}

	@Override
	public boolean delete(final Order entity) {
        return deleteByID(entity.getID());
	}

	@Override
	public boolean deleteByID(final Pair<Integer, Integer> id) {
        final String sql = "DELETE FROM " + DatabaseConstants.USER_TABLE +
            " WHERE " + DatabaseConstants.OrderColumns.USER_ID + " = ?" +
            " AND " + DatabaseConstants.OrderColumns.BOOK_ID + " = ?";

        try (var conn = connectionManager.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id.getFirst());
            pstmt.setInt(2, id.getSecond());
            
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
            DatabaseConstants.OrderColumns.BOOK_ID + ',' +
            DatabaseConstants.OrderColumns.QUANTITY + ',' +
            DatabaseConstants.OrderColumns.ORDER_DATE + ',' + 
            DatabaseConstants.OrderColumns.ORDER_TIME + 
            ") VALUES(?,?,?,?,?);";

            try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, newEntity.getUserId());
                pstmt.setInt(2, newEntity.getBookId());
                pstmt.setInt(3, newEntity.getQuantity());
                pstmt.setDate(4, newEntity.getOrderDate());
                pstmt.setTime(5, newEntity.getOrderTime());
                
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
}



