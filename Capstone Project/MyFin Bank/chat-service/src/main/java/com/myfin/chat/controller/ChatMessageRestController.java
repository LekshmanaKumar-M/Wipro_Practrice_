package com.myfin.chat.controller;

import com.myfin.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatMessageRestController {

    @Autowired
    private ChatMessageService chatService;

    /**
     * CUSTOMER → ADMIN message API
     */
    @PostMapping("/send/customer")
    public Map<String, Object> sendCustomerMessage(@RequestBody Map<String, Object> body) {

        Long customerId = Long.valueOf(body.get("customerId").toString());
        String msg = body.get("message").toString();

        chatService.saveMessage(customerId, "CUSTOMER", msg);

        return Map.of(
                "status", "sent",
                "sender", "CUSTOMER",
                "message", msg
        );
    }

    /**
     * ADMIN → CUSTOMER message API
     */
    @PostMapping("/send/admin")
    public Map<String, Object> sendAdminMessage(@RequestBody Map<String, Object> body) {

        Long customerId = Long.valueOf(body.get("customerId").toString());
        String msg = body.get("message").toString();

        chatService.saveMessage(customerId, "ADMIN", msg);

        return Map.of(
                "status", "sent",
                "sender", "ADMIN",
                "message", msg
        );
    }
}
