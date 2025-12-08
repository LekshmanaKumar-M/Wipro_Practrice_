package com.myfin.admin.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String accountNumber;
    private String email;
    private double balance;
    private boolean active;
}
