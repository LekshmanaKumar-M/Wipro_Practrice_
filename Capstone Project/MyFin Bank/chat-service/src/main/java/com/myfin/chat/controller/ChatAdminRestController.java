package com.myfin.chat.controller;

import com.myfin.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat/admin")
@CrossOrigin
public class ChatAdminRestController {

    @Autowired
    private ChatMessageService chatService;


    /**
     * Get list of customers who have unread messages
     */
    @GetMapping("/unread-list")
    public List<Map<String, Object>> unreadList() {
        return chatService.getUnreadCustomerList();
    }


    /**
     * Get list of all customers who have chatted at least once
     */
    @GetMapping("/customers")
    public List<Map<String, Object>> allCustomers() {
        return chatService.getAllChatCustomers();
    }


    /**
     * Get stats for a particular customer
     * { total, read, unread }
     */
    @GetMapping("/stats/{customerId}")
    public Map<String, Long> stats(@PathVariable Long customerId) {

        if (customerId == null || customerId <= 0) {
            return Map.of("error", -1L);
        }

        return chatService.getStats(customerId);
    }


    /**
     * Mark customer → admin messages as read
     */
    @PutMapping("/mark-read/{customerId}")
    public Map<String, String> markRead(@PathVariable Long customerId) {

        chatService.markAsRead(customerId);

        return Map.of(
                "status", "success",
                "message", "Messages marked as read for customerId=" + customerId
        );
    }
}
