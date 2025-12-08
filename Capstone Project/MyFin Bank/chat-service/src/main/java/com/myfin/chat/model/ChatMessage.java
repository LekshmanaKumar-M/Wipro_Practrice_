package com.myfin.chat.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to Customer ID from customer-service
    private Long customerId;

    // "CUSTOMER" or "ADMIN"
    @Column(length = 20)
    private String sender;

    @Column(length = 2000)
    private String message;

    private LocalDateTime timestamp;

    @Column(columnDefinition = "boolean default false")
    private boolean readFlag = false;

    @PrePersist
    public void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
