package com.myfin.service;

import com.myfin.model.Customer;
import com.myfin.model.Investment;
import com.myfin.repository.CustomerRepository;
import com.myfin.repository.InvestmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private InvestmentRepository investRepo;

    // ⭐ TRANSFER MONEY FUNCTION
    public String transfer(String accountNumber, String type, double amount) {

        Customer customer = customerRepo.findByAccountNumber(accountNumber);
        if (customer == null)
            throw new RuntimeException("Customer not found");

        if (customer.getBalance() < amount)
            throw new RuntimeException("Insufficient Balance");

        Investment inv = investRepo.findByAccountNumber(accountNumber);

        if (inv == null) {
            inv = new Investment();
            inv.setAccountNumber(accountNumber);
        }

        // Deduct from the customer's bank balance
        customer.setBalance(customer.getBalance() - amount);
        customerRepo.save(customer);

        switch (type.toUpperCase()) {

            case "LOAN":
                inv.setLoanBalance(inv.getLoanBalance() + amount);  
                break;

            case "RD":
                inv.setRdBalance(inv.getRdBalance() + amount);
                break;

            case "FD":
                inv.setFdBalance(inv.getFdBalance() + amount);
                break;

            default:
                throw new RuntimeException("Invalid Investment Type");
        }

        investRepo.save(inv);

        return "Amount transferred successfully to " + type;
    }

    
    // Calculate RD
    public double calculateRD(double monthlyDeposit, double annualRate, int months) {

        double principal = monthlyDeposit * months;
        double interest = (principal * annualRate * months) / (12 * 100);

        return Math.round((principal + interest) * 100.0) / 100.0;
    }
    
    // Calculate FD
    public double calculateFD(double principal, double annualRate, double years) {

        double maturity = principal * Math.pow(1 + annualRate / 100, years);

        return Math.round(maturity * 100.0) / 100.0;
    }


}
