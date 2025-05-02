package com.ledger.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private String fromEmail;
    private String toEmail;
    private int amount;
}
