package com.ledger.service;

import com.ledger.model.Account;
import com.ledger.model.Transaction;
import com.ledger.model.AccountRequest;
import com.ledger.model.TransferRequest;
import com.ledger.repository.AccountRepository;
import com.ledger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LedgerService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    public String createAccount(AccountRequest request) {
        // Verificar se a conta já existe
        if (accountRepository.existsByEmail(request.getEmail())) {
            return "Account already exists";
        }

        // Criar uma nova conta
        Account account = new Account(request.getEmail(), request.getPublicKey(), 0);
        accountRepository.save(account);
        return "Account created";
    }

    public String transferTokens(TransferRequest request) {
        // Encontrar as contas no banco de dados
        Optional<Account> fromAccountOpt = accountRepository.findByEmail(request.getFromEmail());
        Optional<Account> toAccountOpt = accountRepository.findByEmail(request.getToEmail());

        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
            Account fromAccount = fromAccountOpt.get();
            Account toAccount = toAccountOpt.get();

            // Verificar se o saldo é suficiente
            if (fromAccount.getBalance() >= request.getAmount()) {
                fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
                toAccount.setBalance(toAccount.getBalance() + request.getAmount());

                // Salvar as contas atualizadas no banco de dados
                accountRepository.save(fromAccount);
                accountRepository.save(toAccount);
                String transactionDetails = "Transferred " + request.getAmount() + " tokens from " + request.getFromEmail() + " to " + request.getToEmail();
                Transaction transaction = new Transaction(fromAccount, toAccount, request.getAmount(), transactionDetails);
                transactionRepository.save(transaction);
                return "Transfer successful";
            } else {
                return "Transfer failed: Insufficient balance";
            }
        } else {
            return "Transfer failed: Account not found";
        }
    }

    public int getBalance(String email) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        return accountOpt.map(Account::getBalance).orElse(-1);
    }

    public String deleteAccount(String email) {
        if (accountRepository.existsByEmail(email)) {
            accountRepository.deleteByEmail(email);
            return "Account deleted";
        } else {
            return "Account not found";
        }
    }

    public String updateAccount(AccountRequest request) {
        Optional<Account> accountOpt = accountRepository.findByEmail(request.getEmail());

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setPublicKey(request.getPublicKey());

            accountRepository.save(account);
            return "Account updated";
        } else {
            return "Account not found";
        }
    }

    public String getAccountInfo(String email) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        return accountOpt.map(account -> "Email: " + account.getEmail() + ", Public Key: " + account.getPublicKey() + ", Balance: " + account.getBalance())
                .orElse("Account not found");
    }
    public boolean validateAccount(AccountRequest request) {
        Optional<Account> accountOpt = accountRepository.findByEmail(request.getEmail());
        return accountOpt.isPresent() && accountOpt.get().getPublicKey().equals(request.getPublicKey());
    }
}
