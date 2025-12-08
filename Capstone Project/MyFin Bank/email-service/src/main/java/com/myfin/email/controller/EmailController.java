package com.myfin.email.controller;

import com.myfin.email.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * Called by customer-service when customer balance becomes ZERO
     */
    @PostMapping("/zero-balance")
    public String zeroBalanceMail(
            @RequestParam String customerName,
            @RequestParam String accountNumber,
            @RequestParam String customerEmail,
            @RequestParam double balance
    ) {
        emailSenderService.sendZeroBalanceMail(
                customerName,
                accountNumber,
                customerEmail,
                balance
        );
        return "Zero-balance notification sent";
    }
}
