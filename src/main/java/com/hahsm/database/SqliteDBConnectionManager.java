package com.hahsm.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDBConnectionManager implements DatabaseConnectionManager {

    private static SqliteDBConnectionManager uniqueInstance;
    private Connection connection;

    private SqliteDBConnectionManager() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            props.load(input);

            String connString = props.getProperty("db.sqlite.url");

            connection = DriverManager.getConnection(connString);
            System.out.println("Connected to db");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public static SqliteDBConnectionManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SqliteDBConnectionManager();
        }
        return uniqueInstance;
    }

}
