package com.ledger.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;

@Component
public class Client {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Value("${client.keystore.name}")
    private String clientKeystoreName;
    @Value("${server.ssl.key-store-type}")
    private String serverKeystoreType;
    @Value("${client.keystore.alias}")
    private String clientKeystoreAlias;
    @Value("${client.keystore.password}")
    private String clientKeystorePassword;
    @Value("${server.ssl.key-store-algorithm}")
    private String serverKeystoreAlgorithm;
    @Value("${server.keystore.password}")
    private String serverKeystorePassword;
    @Value("${server.keystore.alias}")
    private String serverKeystoreAlias;
    @Value("${server.keystore.name}")
    private String serverKeystoreName;

    @PostConstruct
    public void init() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(this.serverKeystoreType);
        keyStore.load(getClass().getClassLoader().getResourceAsStream(this.clientKeystoreName), this.clientKeystorePassword.toCharArray());
        this.privateKey = (PrivateKey) keyStore.getKey(this.clientKeystoreAlias, this.clientKeystorePassword.toCharArray());

        keyStore.load(getClass().getClassLoader().getResourceAsStream(this.serverKeystoreName), this.serverKeystorePassword.toCharArray());
        this.publicKey = keyStore.getCertificate(this.serverKeystoreAlias).getPublicKey();
    }

    public String signData(String data) throws Exception {
        Signature signature = Signature.getInstance(this.serverKeystoreAlgorithm);
        signature.initSign(this.privateKey);
        signature.update(data.getBytes());
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    public boolean verifySignature(String data, String signedData) throws Exception {
        try {
            Signature signature = Signature.getInstance(serverKeystoreAlgorithm);
            signature.initVerify(this.publicKey);
            signature.update(data.getBytes());
            byte[] signedBytes = Base64.getDecoder().decode(signedData);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            System.out.println("Exception during signature verification: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
