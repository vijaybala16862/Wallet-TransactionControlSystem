package com.example.wallet.service;

import com.example.wallet.dao.UserDao;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String username, String plainPassword) {

        if (username == null || plainPassword == null) {
            throw new BusinessException("Username and password required");
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        userDao.save(user);
    }

    public User login(String username, String plainPassword) {

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new BusinessException("Invalid username or password");
        }

        boolean matched = BCrypt.checkpw(plainPassword, user.getPassword());
        if (!matched) {
            throw new BusinessException("Invalid username or password");
        }

        return user;
    }
}
