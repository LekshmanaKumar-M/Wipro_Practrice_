package com.myfin.admin.service;

import com.myfin.admin.dto.LoanDTO;
import com.myfin.admin.feign.LoanFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLoanService {

    @Autowired
    private LoanFeignClient loanClient;

    public List<LoanDTO> getAllLoans() {
        return loanClient.getAllLoans();
    }

    public String approveLoan(Long id) {
        return loanClient.updateLoanStatus(id, "APPROVED");
    }

    public String rejectLoan(Long id) {
        return loanClient.updateLoanStatus(id, "REJECTED");
    }
}
