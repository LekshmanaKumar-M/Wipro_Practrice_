package com.myfin.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    // admin receiver configured in application.yml
    @Value("${myfin.admin.email}")
    private String adminEmail;

    public void sendZeroBalanceMail(
            String customerName,
            String accountNumber,
            String customerEmail,
            double balance
    ) {

        String subject = "MyFin Alert: Customer Balance Reached Zero";

        String body = """
                Dear Admin,

                The following customer balance has reached ZERO:

                Name          : %s
                Account Number: %s
                Customer Email: %s
                Current Balance: %.2f

                Please review this account in the admin portal.

                Regards,
                MyFin Notification Service
                """.formatted(customerName, accountNumber, customerEmail, balance);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(adminEmail);
        msg.setSubject(subject);
        msg.setText(body);

        mailSender.send(msg);
    }
}
