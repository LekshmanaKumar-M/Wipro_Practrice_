package com.myfin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String accountNumber;

    private String password;

    @Column(unique = true)
    private String email;   

    private double balance;
    private boolean active = true;

    public Customer() {}
}

