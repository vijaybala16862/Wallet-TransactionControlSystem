package com.example.wallet.model;

public class Wallet {

    private String walletId;
    private String userId;
    private double balance;
    private String status;

    public Wallet() {}

    public Wallet(String walletId, String userId, double balance, String status) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
        this.status = status;
    }

    public String getWalletId() { return walletId; }
    public String getUserId() { return userId; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }

    public void setBalance(double balance) { this.balance = balance; }
}
