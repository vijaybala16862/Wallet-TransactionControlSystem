package com.example.wallet.model;

public class Wallet {

    private String walletId;
    private double balance;
    private String status;

    public Wallet(String walletId, double balance, String status) {
        this.walletId = walletId;
        this.balance = balance;
        this.status = status;
    }

    public String getWalletId() {
        return walletId;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
