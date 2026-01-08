package com.example.wallet.dao;

import com.example.wallet.model.User;

public interface UserDao {

    void save(User user);

    User findByUsername(String username);

}
