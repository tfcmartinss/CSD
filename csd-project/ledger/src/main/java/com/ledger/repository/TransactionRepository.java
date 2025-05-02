package com.ledger.repository;

import com.ledger.model.Account;
import com.ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
    void deleteByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}
