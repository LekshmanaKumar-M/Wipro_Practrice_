package com.myfin.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String type;        // DEPOSIT / WITHDRAW / TRANSFER-SENT / TRANSFER-RECEIVED
    private double amount;

    private String transactionId; 

    private LocalDateTime timestamp = LocalDateTime.now();
}
