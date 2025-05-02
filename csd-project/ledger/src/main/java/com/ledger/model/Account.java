package com.ledger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String publicKey;

    private int balance;

    public Account() {
        // construtor vazio obrigatório para JPA
    }

    public Account(String email, String publicKey, int balance) {
        this.email = email;
        this.publicKey = publicKey;
        this.balance = balance;
    }
}
