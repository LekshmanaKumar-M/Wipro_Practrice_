package com.myfin.admin.feign;

import com.myfin.admin.dto.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "loanClient", url = "http://localhost:8081")
public interface LoanFeignClient {

    @GetMapping("/api/loans/all")
    List<LoanDTO> getAllLoans();

    @PutMapping("/api/loans/update-status/{id}/{status}")
    String updateLoanStatus(@PathVariable Long id, @PathVariable String status);
}

