package com.hahsm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hahsm.common.ConfigLoader;

public class DatabaseConnectionManager {
    private final String dbName;
    private Connection connection;

    public DatabaseConnectionManager(String dbName) {
        this.dbName = dbName;
    }

    public void initializeConnection() throws ClassNotFoundException, SQLException {
        String url = ConfigLoader.getProperty(dbName, "url");
        String driver = ConfigLoader.getProperty(dbName, "driver");
        String username = ConfigLoader.getProperty(dbName, "username");
        String password = ConfigLoader.getProperty(dbName, "password");
        
        Class.forName(driver);


        if (username != null && password != null) {
            connection = DriverManager.getConnection(url, username, password);
        } else {
            connection = DriverManager.getConnection(url);
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
