package com.myfin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "email-service")
public interface EmailFeignClient {

    @PostMapping("/api/email/zero-balance")
    String sendZeroBalanceAlert(
            @RequestParam("customerName") String customerName,
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("customerEmail") String customerEmail,
            @RequestParam("balance") double balance
    );
}
