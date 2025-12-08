package com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.bean.MailDetail;
import com.company.service.MailService;

@RestController
@RequestMapping("/api")
public class MailController {
@Autowired
private MailService mailService;

//Sending email
/*
 * {

"recipient": "tjak2005@gmail.com",
"subject":"Test mail",
"msgBody":" Project for mailing service"*/
@PostMapping("/send-mail")
public String sendMail(@RequestBody MailDetail mailDetail) {
return mailService.sendMail(mailDetail);
}
//Sending email with attachment
/*
 * {

"recipient": "mdaarifraza03@gmail.com",
"subject":"Test mail",
"msgBody":" Project for mailing service",
"attachment" : "C:\\Users\\Niti Dwivedi\\Pictures\\IO\\Inputoutput.png"

}
 */
@PostMapping("/send-mail-attachment")
public String sendMailWithAttachment(@RequestBody MailDetail mailDetail)
{
return mailService.sendMailWithAttachment(mailDetail);
}
}