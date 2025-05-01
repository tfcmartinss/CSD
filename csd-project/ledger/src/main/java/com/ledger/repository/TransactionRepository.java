package com.ledger.repository;

import com.ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Pode adicionar métodos customizados se necessário
}
