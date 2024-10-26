package com.hahsm.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDBConnection implements DatabaseConnection {

    private static SqliteDBConnection uniqueInstance;
    private Connection connection;

    private SqliteDBConnection() {
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

    public static SqliteDBConnection getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SqliteDBConnection();
        }
        return uniqueInstance;
    }

}
