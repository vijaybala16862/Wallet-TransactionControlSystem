package com.example.wallet;

import com.example.wallet.exception.BusinessException;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletServiceTest {

    @Test
    void createWallet_success() {
        WalletService service = new WalletService();

        var wallet = service.createWallet("USER1");

        assertNotNull(wallet);
        assertNotNull(wallet.getWalletId());
        assertEquals(0.0, wallet.getBalance());
        assertEquals("ACTIVE", wallet.getStatus());
    }

    @Test
    void credit_success() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        service.credit(wallet.getWalletId(), 500);

        assertEquals(500.0, wallet.getBalance());
    }

    @Test
    void debit_success() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        service.credit(wallet.getWalletId(), 500);
        service.debit(wallet.getWalletId(), 200);

        assertEquals(300.0, wallet.getBalance());
    }

    @Test
    void createWallet_shouldFail_whenUserIdIsNull() {
        WalletService service = new WalletService();

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.createWallet(null)
        );

        assertEquals("UserId is mandatory", exception.getMessage());
    }

    @Test
    void createWallet_shouldFail_whenUserIdIsBlank() {
        WalletService service = new WalletService();

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.createWallet("   ")
        );

        assertEquals("UserId is mandatory", exception.getMessage());
    }

    @Test
    void credit_shouldFail_whenAmountIsZero() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.credit(wallet.getWalletId(), 0)
        );

        assertEquals("Invalid credit amount", exception.getMessage());
    }

    @Test
    void credit_shouldFail_whenAmountIsNegative() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.credit(wallet.getWalletId(), -100)
        );

        assertEquals("Invalid credit amount", exception.getMessage());
    }

    @Test
    void debit_shouldFail_whenAmountIsZero() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.debit(wallet.getWalletId(), 0)
        );

        assertEquals("Invalid debit amount", exception.getMessage());
    }

    @Test
    void debit_shouldFail_whenAmountIsNegative() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.debit(wallet.getWalletId(), -50)
        );

        assertEquals("Invalid debit amount", exception.getMessage());
    }

    @Test
    void debit_shouldFail_whenInsufficientBalance() {
        WalletService service = new WalletService();
        var wallet = service.createWallet("USER1");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.debit(wallet.getWalletId(), 100)
        );

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void debit_shouldFail_whenWalletNotFound() {
        WalletService service = new WalletService();

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.debit("INVALID_WALLET_ID", 100)
        );

        assertEquals("Wallet not found", exception.getMessage());
    }

    @Test
    void credit_shouldFail_whenWalletNotFound() {
        WalletService service = new WalletService();

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.credit("INVALID_WALLET_ID", 100)
        );

        assertEquals("Wallet not found", exception.getMessage());
    }
}
