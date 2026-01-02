package com.example.wallet.util;

import com.example.wallet.exception.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/wallet";
    private static final String USER = "root";
    private static final String PASSWORD = "Bala@123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (Exception e)
        {
            throw new BusinessException("conn fail",e);
        }
    }
}
