package com.hahsm.user.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.repository.Repository;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.user.model.User;

public class UserRepository implements Repository<User, Integer> {
    /**
     *
     */
    private final DatabaseConnectionManager connectionManager;

    public UserRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

	@Override
	public List<User> getAll() {
        String sql = "SELECT " +
            DatabaseConstants.UserColumns.ID + ',' +
            DatabaseConstants.UserColumns.NAME + ',' +
            DatabaseConstants.UserColumns.ADDRESS +
            " FROM " + DatabaseConstants.USER_TABLE;

        List<User> queryResult = new ArrayList<User>(); 

        try (var conn = connectionManager.getConnection()) {
            var stmt = conn.createStatement();           
            var result = stmt.executeQuery(sql);
            
            while (result.next()) {
                queryResult.add(new User(
                    result.getInt(DatabaseConstants.UserColumns.ID),
                    result.getString(DatabaseConstants.UserColumns.NAME),
                    result.getString(DatabaseConstants.UserColumns.ADDRESS)
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
	public Optional<User> getByID(Integer id) {
        String sql = "SELECT " +
            DatabaseConstants.UserColumns.ID + ',' +
            DatabaseConstants.UserColumns.NAME + ',' +
            DatabaseConstants.UserColumns.ADDRESS +
            " FROM " + DatabaseConstants.USER_TABLE +
            " WHERE " + DatabaseConstants.UserColumns.ID + " = ?";


        try (var conn = connectionManager.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);

            var result = pstmt.executeQuery();
            if (result.next()) {
                return Optional.of(new User(
                    result.getInt(DatabaseConstants.UserColumns.ID),
                    result.getString(DatabaseConstants.UserColumns.NAME),
                    result.getString(DatabaseConstants.UserColumns.ADDRESS)
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
	public boolean update(User entity) {
        String sql = "UPDATE " + DatabaseConstants.USER_TABLE + " SET " +
            DatabaseConstants.UserColumns.NAME + " = ?," + 
            DatabaseConstants.UserColumns.ADDRESS + " = ?," +
            " WHERE " + DatabaseConstants.UserColumns.ID + " = ?;";

        try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getAddress());
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
	public boolean delete(User entity) {
        return deleteByID(entity.getID());
	}

	@Override
	public boolean deleteByID(Integer id) {
        String sql = "DELETE FROM " + DatabaseConstants.USER_TABLE
            + " WHERE " + DatabaseConstants.UserColumns.ID + " = ?";

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

	@Override
	public User insert(User newEntity) {
        String sql = "INSERT INTO " + DatabaseConstants.USER_TABLE + '(' +
            DatabaseConstants.UserColumns.NAME + ',' + 
            DatabaseConstants.UserColumns.ADDRESS + ')' +
            " VALUES(?,?);";

            try (var conn = connectionManager.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, newEntity.getName());
                pstmt.setString(2, newEntity.getAddress());
                
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Unable to insert user");    
                }

                try (var generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        newEntity.setID(id);
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
}



