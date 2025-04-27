package com.ledger.model;

public class Account {
    private String publicKey;
    private int balance;

    public Account(String publicKey, int balance) {
        this.publicKey = publicKey;
        this.balance = balance;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}