package com.myfin.admin.dto;

import lombok.Data;

@Data
	public class LoanDTO {

	    private Long id;
	    private String accountNumber;
	    private double amount;
	    private String loanType;
	    private String purpose;
	    private String status;

	    private double balance;   
	    private String name;      
	    private String email;     
	}



