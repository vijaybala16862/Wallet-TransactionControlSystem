package org.example;

import com.example.wallet.dao.UserDao;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.User;
import com.example.wallet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setup() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void register_success() {
        assertDoesNotThrow(() ->
                userService.register("bala", "pwd")
        );
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    void register_null_values() {
        assertThrows(BusinessException.class,
                () -> userService.register(null, null));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setUsername("bala");
        user.setPassword(BCrypt.hashpw("pwd", BCrypt.gensalt()));

        when(userDao.findByUsername("bala")).thenReturn(user);

        User result = userService.login("bala", "pwd");
        assertNotNull(result);
    }

    @Test
    void login_invalid_password() {
        User user = new User();
        user.setUsername("bala");
        user.setPassword(BCrypt.hashpw("pwd", BCrypt.gensalt()));

        when(userDao.findByUsername("bala")).thenReturn(user);

        assertThrows(BusinessException.class,
                () -> userService.login("bala", "wrong"));
    }
}
