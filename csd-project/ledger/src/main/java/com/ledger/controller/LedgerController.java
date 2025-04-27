package com.ledger.controller;

import com.ledger.model.AccountRequest;
import com.ledger.model.TransferRequest;
import com.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/create_account")
    public String createAccount(@RequestBody AccountRequest request) {
        return ledgerService.createAccount(request);
    }

    @PostMapping("/transfer_tokens")
    public String transferTokens(@RequestBody TransferRequest request) {
        return ledgerService.transferTokens(request);
    }

    @GetMapping("/get_balance/{email}")
    public int getBalance(@PathVariable String email) {
        return ledgerService.getBalance(email);
    }

    @GetMapping("/list_transactions/{email}")
    public String listTransactions(@PathVariable String email) {
        return ledgerService.listTransactions(email);
    }

    @DeleteMapping("/delete_account/{email}")
    public String deleteAccount(@PathVariable String email) {
        return ledgerService.deleteAccount(email);
    }

    @PutMapping("/update_account")
    public String updateAccount(@RequestBody AccountRequest request) {
        return ledgerService.updateAccount(request);
    }

    @GetMapping("/get_account_info/{email}")
    public String getAccountInfo(@PathVariable String email) {
        return ledgerService.getAccountInfo(email);
    }

    @PostMapping("/validate_account")
    public boolean validateAccount(@RequestBody AccountRequest request) {
        return ledgerService.validateAccount(request);
    }
}