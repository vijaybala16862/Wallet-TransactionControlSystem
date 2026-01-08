package com.example.wallet.dao;

import com.example.wallet.exception.DataAccessException;
import com.example.wallet.model.User;
import com.example.wallet.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    @Override
    public void save(User user) {

        String sql = """
                INSERT INTO users (username, password) VALUES (?, ?) """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // HASHED

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error saving user", e);
        }
    }

    @Override
    public User findByUsername(String username) {

        String sql = """
                SELECT id, username, password FROM users WHERE username = ? """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
                return null;
            }

        } catch (Exception e) {
            throw new DataAccessException("Error fetching user", e);
        }
    }
 }
