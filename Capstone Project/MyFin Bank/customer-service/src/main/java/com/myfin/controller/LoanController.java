package com.myfin.controller;

import com.myfin.dto.LoanDTO;
import com.myfin.model.Customer;
import com.myfin.model.Loan;
import com.myfin.repository.CustomerRepository;
import com.myfin.repository.LoanRepository;
import com.myfin.service.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository repo;

    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private LoanService loanService;

    @GetMapping("/all")
    public List<LoanDTO> getAllLoans() {

        return repo.findAll().stream().map(loan -> {

            LoanDTO dto = new LoanDTO();
            dto.setId(loan.getId());
            dto.setAccountNumber(loan.getAccountNumber());
            dto.setAmount(loan.getAmount());
            dto.setLoanType(loan.getLoanType());
            dto.setPurpose(loan.getPurpose());
            dto.setStatus(loan.getStatus());

            // fetch customer info
            Customer c = customerRepo.findByAccountNumber(loan.getAccountNumber());

            if (c != null) {
                dto.setName(c.getName());
                dto.setEmail(c.getEmail());
                dto.setBalance(c.getBalance());
            }

            return dto;

        }).collect(Collectors.toList());
    }


    @PutMapping("/update-status/{id}/{status}")
    public String updateStatus(@PathVariable Long id, @PathVariable String status) {

        Loan loan = repo.findById(id).orElse(null);

        if (loan == null) {
            return "Loan not found";
        }

        loan.setStatus(status.toUpperCase());
        repo.save(loan);

        return "Loan " + status + " successfully!";
    }
    
    
    
    @GetMapping("/emi")
    @ResponseBody
    public String getEmi(@RequestParam double amount,
                         @RequestParam double rate,
                         @RequestParam int months) {

        double emi = loanService.calculateEMI(amount, rate, months);
        return "Your EMI is: ₹" + emi;
    }

}
