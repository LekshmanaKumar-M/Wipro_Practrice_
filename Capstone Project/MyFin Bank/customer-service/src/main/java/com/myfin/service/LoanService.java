package com.myfin.service;

import com.myfin.model.Customer;
import com.myfin.model.Loan;
import com.myfin.repository.LoanRepository;
import com.myfin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repo;

    @Autowired
    private CustomerRepository customerRepo;

    public Loan applyLoan(String accountNumber, double amount, String loanType, String purpose) {

        Customer customer = customerRepo.findByAccountNumber(accountNumber);

        Loan loan = new Loan();
        loan.setAccountNumber(accountNumber);
        loan.setAmount(amount);
        loan.setLoanType(loanType);
        loan.setPurpose(purpose);
        loan.setStatus("APPLIED");

        
        if (customer != null) {
            loan.setName(customer.getName());
            loan.setEmail(customer.getEmail());
        }

        return repo.save(loan);
    }

    public List<Loan> getLoans(String accountNumber) {
        return repo.findByAccountNumber(accountNumber);
    }
    
    // EMI Calculation Method
    public double calculateEMI(double principal, double annualRate, int months) {

        double monthlyRate = annualRate / 12 / 100;

        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                     (Math.pow(1 + monthlyRate, months) - 1);

        return Math.round(emi * 100.0) / 100.0;  // round to 2 decimals
    }


}
