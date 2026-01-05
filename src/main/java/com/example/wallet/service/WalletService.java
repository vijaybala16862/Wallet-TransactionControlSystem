package com.example.wallet.service;

import com.example.wallet.model.Wallet;

public interface WalletService {
    Wallet create(String userId);
    Wallet get(String walletId);
    void credit(String walletId, double amount);
    void debit(String walletId, double amount);
    void delete(String walletId);
}
