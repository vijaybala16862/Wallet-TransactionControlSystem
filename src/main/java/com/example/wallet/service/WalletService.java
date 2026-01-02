package com.example.wallet.service;

import com.example.wallet.dao.WalletDao;
import com.example.wallet.dao.WalletDaoImpl;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.Wallet;

public class WalletService {

    private final WalletDao walletDao = new WalletDaoImpl();

    public Wallet createWallet(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BusinessException("UserId is mandatory");
        }

        Wallet wallet = new Wallet(
                "WAL-" + System.currentTimeMillis(),
                0.0,
                "ACTIVE"
        );

        walletDao.save(wallet);
        return wallet;
    }

    public Wallet getWallet(String walletId) {
        Wallet wallet = walletDao.findById(walletId);
        if (wallet == null) {
            throw new BusinessException("Wallet not found");
        }
        return wallet;
    }

    public void credit(String walletId, double amount) {
        Wallet wallet = getWallet(walletId);

        if (amount <= 0) {
            throw new BusinessException("Invalid credit amount");
        }

        wallet.setBalance(wallet.getBalance() + amount);
        walletDao.update(wallet);
    }

    public void debit(String walletId, double amount) {
        Wallet wallet = getWallet(walletId);

        if (amount <= 0) {
            throw new BusinessException("Invalid debit amount");
        }
        if (wallet.getBalance() < amount) {
            throw new BusinessException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletDao.update(wallet);
    }

    public void deleteWallet(String walletId) {
        getWallet(walletId);
        walletDao.delete(walletId);
    }
}
