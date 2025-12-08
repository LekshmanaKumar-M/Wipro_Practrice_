package com.myfin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private double loanBalance = 0;   // outstanding loan
    private double rdBalance = 0;     // recurring deposit
    private double fdBalance = 0;     // fixed deposit
}
