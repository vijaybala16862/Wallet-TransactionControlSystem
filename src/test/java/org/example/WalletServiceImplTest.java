package org.example;

import com.example.wallet.dao.WalletDao;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.Wallet;

import com.example.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletDao walletDao;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void createWallet_success() {
        Wallet wallet = walletService.create("USER1");

        assertNotNull(wallet);
        assertEquals("ACTIVE", wallet.getStatus());
        assertEquals(0.0, wallet.getBalance());

        verify(walletDao).save(any(Wallet.class));
    }

    @Test
    void getWallet_success() {
        Wallet mockWallet = new Wallet("W1", "USER1", 100.0, "ACTIVE");

        when(walletDao.findById("W1")).thenReturn(mockWallet);

        Wallet wallet = walletService.get("W1");

        assertEquals(100.0, wallet.getBalance());
        verify(walletDao).findById("W1");
    }

    @Test
    void credit_success() {
        Wallet mockWallet = new Wallet("W1", "USER1", 100.0, "ACTIVE");

        when(walletDao.findById("W1")).thenReturn(mockWallet);

        walletService.credit("W1", 50);

        assertEquals(150.0, mockWallet.getBalance());
        verify(walletDao).update(mockWallet);
    }

    @Test
    void debit_success() {
        Wallet mockWallet = new Wallet("W1", "USER1", 200.0, "ACTIVE");

        when(walletDao.findById("W1")).thenReturn(mockWallet);

        walletService.debit("W1", 100);

        assertEquals(100.0, mockWallet.getBalance());
        verify(walletDao).update(mockWallet);
    }

    @Test
    void createWallet_shouldFail_whenUserIdNull() {
        assertThrows(BusinessException.class,
                () -> walletService.create(null));
    }

    @Test
    void getWallet_shouldFail_whenNotFound() {
        when(walletDao.findById("W404")).thenReturn(null);

        assertThrows(BusinessException.class,
                () -> walletService.get("W404"));
    }

    @Test
    void credit_shouldFail_whenAmountInvalid() {
        assertThrows(BusinessException.class,
                () -> walletService.credit("W1", -10));
    }

    @Test
    void debit_shouldFail_whenInsufficientBalance() {
        Wallet mockWallet = new Wallet("W1", "USER1", 50.0, "ACTIVE");

        when(walletDao.findById("W1")).thenReturn(mockWallet);

        assertThrows(BusinessException.class,
                () -> walletService.debit("W1", 100));
    }
}
