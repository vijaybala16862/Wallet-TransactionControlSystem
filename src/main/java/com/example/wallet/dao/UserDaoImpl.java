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
                INSERT INTO users (username, password, role)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // HASHED password
            ps.setString(3, user.getRole());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DataAccessException("Error saving user", e);
        }
    }

    @Override
    public User findByUsername(String username) {

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username = ?
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); // HASH
                    user.setRole(rs.getString("role"));
                    return user;
                }
                return null;
            }

        } catch (Exception e) {
            throw new DataAccessException("Error fetching user by username", e);
        }
    }

    @Override
    public User findById(int id) {

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE id = ?
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
                return null;
            }

        } catch (Exception e) {
            throw new DataAccessException("Error fetching user by id", e);
        }
    }
}
