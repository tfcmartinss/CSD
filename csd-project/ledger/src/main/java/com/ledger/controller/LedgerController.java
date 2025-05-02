package com.ledger.controller;

import com.ledger.model.AccountRequest;
import com.ledger.model.TransferRequest;
import com.ledger.service.Client;
import com.ledger.service.LedgerService;
import com.ledger.service.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;
    private final Client client;
    private final Server server;

    @PostMapping("/create_account")
    public Map<String, String> createAccount(@RequestBody AccountRequest request) throws Exception {
        String signedRequest = client.signData(request.toString());
        boolean isValid = server.verifySignature(request.toString(), signedRequest);

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            String result = ledgerService.createAccount(request);
            String signedResponse = server.signData(result);

            response.put("result", result);
            response.put("signature", signedResponse);
        } else {
            response.put("result", "Invalid signature");
        }

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @PostMapping("/transfer_tokens")
    public Map<String, String> transferTokens(@RequestBody TransferRequest request) throws Exception {
        String signedRequest = client.signData(request.toString());
        boolean isValid = server.verifySignature(request.toString(), signedRequest);

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            String result = ledgerService.transferTokens(request);
            String signedResponse = server.signData(result);

            response.put("result", result);
            response.put("signature", signedResponse);
        } else {
            response.put("result", "Invalid signature");
        }

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @GetMapping("/list_transactions/{email}")
    public Map<String, String> listTransactions(@PathVariable String email) throws Exception {
        String result = ledgerService.listTransactions(email);

        String signedResponse = server.signData(result);

        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        response.put("signature", signedResponse);

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @GetMapping("/get_balance/{email}")
    public Map<String, String> getBalance(@PathVariable String email) throws Exception {
        String result = String.valueOf(ledgerService.getBalance(email));

        String signedResponse = server.signData(result);

        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        response.put("signature", signedResponse);

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @DeleteMapping("/delete_account/{email}")
    public Map<String, String> deleteAccount(@PathVariable String email) throws Exception {
        String result = ledgerService.deleteAccount(email);

        String signedResponse = server.signData(result);

        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        response.put("signature", signedResponse);

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @PutMapping("/update_account")
    public Map<String, String> updateAccount(@RequestBody AccountRequest request) throws Exception {
        String signedRequest = client.signData(request.toString());
        boolean isValid = server.verifySignature(request.toString(), signedRequest);

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            String result = ledgerService.updateAccount(request);
            String signedResponse = server.signData(result);

            response.put("result", result);
            response.put("signature", signedResponse);
        } else {
            response.put("result", "Invalid signature");
        }

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @GetMapping("/get_account_info/{email}")
    public Map<String, String> getAccountInfo(@PathVariable String email) throws Exception {
        String result = ledgerService.getAccountInfo(email);

        String signedResponse = server.signData(result);

        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        response.put("signature", signedResponse);

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    @PostMapping("/validate_account")
    public Map<String, String> validateAccount(@RequestBody AccountRequest request) throws Exception {
        String signedRequest = client.signData(request.toString());
        boolean isValid = server.verifySignature(request.toString(), signedRequest);

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            String result = String.valueOf(ledgerService.validateAccount(request));
            String signedResponse = server.signData(result);

            response.put("result", result);
            response.put("signature", signedResponse);
        } else {
            response.put("result", "Invalid signature");
        }

        processServerResponse(response.get("result"), response.get("signature"));
        return response;
    }

    private void processServerResponse(String responseData, String responseSignature) throws Exception {
        boolean isValid = client.verifySignature(responseData, responseSignature);
        if (isValid) {
            System.out.println("Response is valid: " + responseData);
        } else {
            System.out.println("Invalid response signature!");
        }
    }
}