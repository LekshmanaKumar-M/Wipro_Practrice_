package com.myfin.service;

import com.myfin.model.Transaction;
import com.myfin.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    public String recordTransaction(Long customerId, String type, double amount) {

        String txId = "TXN-" + UUID.randomUUID().toString().substring(0, 8);

        Transaction t = new Transaction();
        t.setCustomerId(customerId);
        t.setType(type);
        t.setAmount(amount);
        t.setTransactionId(txId);

        repo.save(t);

        return txId;
    }
}
