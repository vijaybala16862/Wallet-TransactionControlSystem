package com.example.wallet.util;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {

    private static DataSource dataSource;

    private DBUtil() {}

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static Connection getConnection() throws Exception {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource not initialized");
        }
        return dataSource.getConnection();
    }
}
