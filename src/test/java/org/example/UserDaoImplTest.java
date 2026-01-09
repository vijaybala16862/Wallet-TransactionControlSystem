package org.example;

import com.example.wallet.dao.UserDaoImpl;
import com.example.wallet.exception.DataAccessException;
import com.example.wallet.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private final UserDaoImpl userDao = new UserDaoImpl();

    @Test
    void save_user_db_failure() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("pwd");

        assertThrows(DataAccessException.class,
                () -> userDao.save(user));
    }

    @Test
    void find_user_not_found() {
        assertThrows(DataAccessException.class,
                () -> userDao.findByUsername("unknown"));
    }
}
