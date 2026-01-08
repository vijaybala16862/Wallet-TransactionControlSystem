package com.example.wallet.dao;

import com.example.wallet.model.Wallet;

public interface WalletDao {
    void save(Wallet wallet);

    Wallet findById(String walletId);

    void update(Wallet wallet);

    void delete(String walletId);

    void saveTransaction(String walletId,
                         String txnType,
                         double amount,
                         double balanceAfter);
}
