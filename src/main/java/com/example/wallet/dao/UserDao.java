package com.example.wallet.dao;

import com.example.wallet.model.User;
import com.example.wallet.util.DBUtil;
import com.example.wallet.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    private static final Logger LOG =
            LoggerFactory.getLogger(UserDao.class);

    private static final int USERNAME_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;
    private static final int ROLE_INDEX = 3;

    private static final String FIND_BY_USERNAME_SQL =
            "SELECT * FROM users WHERE username = ?";

    private static final String INSERT_USER_SQL =
            "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

    public User findByUsername(String username) {

        LOG.info("Finding user by username");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(FIND_BY_USERNAME_SQL)) {

            ps.setString(USERNAME_INDEX, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }

        } catch (Exception e) {
            LOG.error("Failed to fetch user by username: {}", username, e);
            throw new DataAccessException(
                    "Error while fetching user from database", e);
        }

        return null;
    }

    public void save(User user) {

        LOG.info("Registering new user");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(INSERT_USER_SQL)) {

            ps.setString(USERNAME_INDEX, user.getUsername());
            ps.setString(PASSWORD_INDEX, user.getPassword());
            ps.setString(ROLE_INDEX, user.getRole());

            ps.executeUpdate();
            LOG.info("User registered successfully: {}", user.getUsername());

        } catch (Exception e) {
            LOG.error("User registration failed for: {}",
                    user.getUsername(), e);

            throw new DataAccessException(
                    "Failed to register user in database", e);
        }
    }
}
