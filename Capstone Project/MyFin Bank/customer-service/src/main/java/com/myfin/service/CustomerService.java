package com.myfin.service;

import com.myfin.exception.CustomerNotFoundException;
import com.myfin.exception.IncorrectPasswordException;
import com.myfin.model.Customer;
import com.myfin.repository.CustomerRepository;
import com.myfin.feign.EmailFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EmailFeignClient emailClient;   

    private String lastTransactionId;



    // REGISTER
    public Customer register(Customer c) {
        return repo.save(c);
    }



    // FETCH BY ID
    public Customer getCustomerById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }



    // FETCH ALL CUSTOMERS
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }



    // LOGIN USING ACCOUNT NUMBER
    public Customer login(String accountNumber, String password) {

        Customer c = repo.findByAccountNumber(accountNumber);

        if (c == null) {
            throw new CustomerNotFoundException("Account not found");
        }

        if (!c.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        return c;
    }



    // GET CUSTOMER BY ACCOUNT NUMBER
    public Customer getCustomerByAccountNumber(String acc) {
        Customer c = repo.findByAccountNumber(acc);
        if (c == null) throw new RuntimeException("Account not found");
        return c;
    }



    
    //PRIVATE METHOD TO SEND ZERO BALANCE EMAIL ALERT
 
    private void checkZeroBalanceAndNotify(Customer c) {

        if (c.getBalance() == 0) {

            String email = (c.getEmail() != null) ? c.getEmail() : "noemail@unknown.com";

            emailClient.sendZeroBalanceAlert(
                    c.getName(),
                    c.getAccountNumber(),
                    email,
                    c.getBalance()
            );
        }
    }



    
    // DEPOSIT
    
    public double deposit(Customer customer, double amount) {

        customer.setBalance(customer.getBalance() + amount);
        repo.save(customer);

        lastTransactionId = transactionService.recordTransaction(
                customer.getId(), "DEPOSIT", amount);

        return customer.getBalance();
    }



    
    // WITHDRAW
    
    public double withdraw(Customer customer, double amount) {

        if (customer.getBalance() < amount) {
            throw new CustomerNotFoundException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance() - amount);
        repo.save(customer);

        lastTransactionId = transactionService.recordTransaction(
                customer.getId(), "WITHDRAW", amount);

        //  ZERO BALANCE ALERT
        checkZeroBalanceAndNotify(customer);

        return customer.getBalance();
    }



    
    // TRANSFER MONEY
    
    public String transferByAccountNumber(Customer sender, String toAccount, double amount) {

        Customer receiver = repo.findByAccountNumber(toAccount);

        if (receiver == null) {
            throw new CustomerNotFoundException("Receiver account not found");
        }

        if (sender.getBalance() < amount) {
            throw new CustomerNotFoundException("Insufficient balance to transfer");
        }

        // Deduct from sender
        sender.setBalance(sender.getBalance() - amount);
        repo.save(sender);

        // Add to receiver
        receiver.setBalance(receiver.getBalance() + amount);
        repo.save(receiver);

        String senderTxId = transactionService.recordTransaction(
                sender.getId(), "TRANSFER-SENT", amount);

        transactionService.recordTransaction(
                receiver.getId(), "TRANSFER-RECEIVED", amount);

        lastTransactionId = senderTxId;

        // 🔥 ZERO BALANCE ALERT FOR SENDER
        checkZeroBalanceAndNotify(sender);

        return "Transfer Successful";
    }



    public String getLastTransactionId() {
        return lastTransactionId;
    }
}
