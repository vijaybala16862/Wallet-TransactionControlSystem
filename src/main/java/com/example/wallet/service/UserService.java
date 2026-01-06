package com.example.wallet.service;

import com.example.wallet.dao.UserDao;
import com.example.wallet.model.User;

public class UserService {

    private final UserDao userDao = new UserDao();

    public User getByUsername(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username required");
        }

        return userDao.findByUsername(username);
    }

    public void register(User user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Invalid user data");
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        userDao.save(user);
    }
}
