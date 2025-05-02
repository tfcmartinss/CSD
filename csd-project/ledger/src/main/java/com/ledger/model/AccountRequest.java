package com.ledger.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    private String email;
    private String publicKey;
    private int balance;
}
