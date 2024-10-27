package com.hahsm.database;

import java.sql.Connection;

public interface DatabaseConnectionManager {
    public Connection getConnection();
}
