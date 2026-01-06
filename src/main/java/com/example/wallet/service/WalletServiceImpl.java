package com.example.wallet.service;

import com.example.wallet.dao.WalletDao;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.model.Wallet;

public class WalletServiceImpl implements WalletService {

    private final WalletDao walletDao;

    public WalletServiceImpl(WalletDao walletDao) {
        this.walletDao = walletDao;
    }

    @Override
    public Wallet create(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BusinessException("UserId is mandatory");
        }

        Wallet wallet = new Wallet(
                "WAL-" + System.currentTimeMillis(),
                userId,
                0.0,
                "ACTIVE"
        );

        walletDao.save(wallet);
        return wallet;
    }

    @Override
    public Wallet get(String walletId) {
        Wallet wallet = walletDao.findById(walletId);
        if (wallet == null) {
            throw new BusinessException("Wallet not found");
        }
        return wallet;
    }

    @Override
    public void credit(String walletId, double amount) {
        if (amount <= 0) {
            throw new BusinessException("Invalid credit amount");
        }

        Wallet wallet = get(walletId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletDao.update(wallet);
    }


    @Override
    public void debit(String walletId, double amount) {
        if (amount <= 0) {
            throw new BusinessException("Invalid debit amount");
        }

        Wallet wallet = get(walletId);
        if (wallet.getBalance() < amount) {
            throw new BusinessException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletDao.update(wallet);
    }


    @Override
    public void delete(String walletId) {
        get(walletId);
        walletDao.delete(walletId);
    }
}
