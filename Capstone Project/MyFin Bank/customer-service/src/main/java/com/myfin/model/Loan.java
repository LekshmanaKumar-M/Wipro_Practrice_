package com.myfin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private double amount;

    private String loanType;
    private String purpose;

    private String status;

   
    private String name;
    private String email;
}
