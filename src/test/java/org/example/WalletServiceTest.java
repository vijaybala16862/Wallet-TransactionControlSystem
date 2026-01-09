package org.example;

import com.example.wallet.dao.WalletDao;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    private WalletDao walletDao;
    private WalletService walletService;

    @BeforeEach
    void setup() {
        walletDao = mock(WalletDao.class);
        walletService = new WalletService(walletDao);
    }

    @Test
    void create_wallet_success() {
        Wallet wallet = walletService.create("USER1");
        assertEquals(0.0, wallet.getBalance());
    }

    @Test
    void create_wallet_invalid_user() {
        assertThrows(BusinessException.class,
                () -> walletService.create(""));
    }

    @Test
    void credit_success() {
        Wallet wallet = new Wallet("W1", "U1", 100, "ACTIVE");
        when(walletDao.findById("W1")).thenReturn(wallet);

        walletService.credit("W1", 50);
        assertEquals(150, wallet.getBalance());
    }

    @Test
    void debit_insufficient_balance() {
        Wallet wallet = new Wallet("W1", "U1", 50, "ACTIVE");
        when(walletDao.findById("W1")).thenReturn(wallet);

        assertThrows(BusinessException.class,
                () -> walletService.debit("W1", 100));
    }
}
