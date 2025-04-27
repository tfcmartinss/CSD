package com.ledger.service;

import com.ledger.model.Account;
import com.ledger.model.AccountRequest;
import com.ledger.model.TransferRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LedgerService {

    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, String> transactions = new HashMap<>(); // For simplicity, storing transactions as strings

    public String createAccount(AccountRequest request) {
        if (accounts.containsKey(request.getEmail())) {
            return "Account already exists";
        }
        accounts.put(request.getEmail(), new Account(request.getPublicKey(), 0));
        return "Account created";
    }

    public String transferTokens(TransferRequest request) {
        Account fromAccount = accounts.get(request.getFromEmail());
        Account toAccount = accounts.get(request.getToEmail());
        if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= request.getAmount()) {
            fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
            toAccount.setBalance(toAccount.getBalance() + request.getAmount());
            String transaction = "Transferred " + request.getAmount() + " from " + request.getFromEmail() + " to " + request.getToEmail();
            transactions.put(request.getFromEmail(), transaction);
            transactions.put(request.getToEmail(), transaction);
            return "Transfer successful";
        } else {
            return "Transfer failed: Insufficient balance or account not found";
        }
    }

    public int getBalance(String email) {
        Account account = accounts.get(email);
        return account != null ? account.getBalance() : -1;
    }

    public String listTransactions(String email) {
        return transactions.getOrDefault(email, "No transactions found");
    }

    public String deleteAccount(String email) {
        if (accounts.remove(email) != null) {
            transactions.remove(email);
            return "Account deleted";
        } else {
            return "Account not found";
        }
    }

    public String updateAccount(AccountRequest request) {
        Account account = accounts.get(request.getEmail());
        if (account != null) {
            account.setPublicKey(request.getPublicKey());
            return "Account updated";
        } else {
            return "Account not found";
        }
    }

    public String getAccountInfo(String email) {
        Account account = accounts.get(email);
        return account != null ? "Email: " + email + ", Public Key: " + account.getPublicKey() + ", Balance: " + account.getBalance() : "Account not found";
    }

    public boolean validateAccount(AccountRequest request) {
        Account account = accounts.get(request.getEmail());
        return account != null && account.getPublicKey().equals(request.getPublicKey());
    }
}