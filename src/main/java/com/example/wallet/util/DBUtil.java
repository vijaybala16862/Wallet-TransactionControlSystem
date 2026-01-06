package com.example.wallet.util;

import com.example.wallet.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

    private static DataSource dataSource;

    private DBUtil() {
    }

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static Connection getConnection() {
        if (dataSource == null) {
            throw new DataAccessException("DataSource not initialized", null);
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to obtain database connection", e);
        }
    }
}
