package com.myfin.chat.controller;

import com.myfin.chat.model.ChatMessage;
import com.myfin.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatPageController {

    @Autowired
    private ChatMessageService chatService;

    // Customer: /chat/{customerId}
    @GetMapping("/chat/{customerId}")
    public String chatForCustomer(@PathVariable Long customerId, Model model) {
        List<ChatMessage> history = chatService.getHistory(customerId);

        model.addAttribute("role", "CUSTOMER");
        model.addAttribute("customerId", customerId);
        model.addAttribute("messages", history);

        return "chat-customer";
    }

    // Admin home selector: /chat
    @GetMapping("/chat")
    public String adminChatHome() {
        return "chat-admin-home";
    }

    // Admin: /chat/admin/{customerId}
    @GetMapping("/chat/admin/{customerId}")
    public String chatForAdmin(@PathVariable Long customerId, Model model) {
        List<ChatMessage> history = chatService.getHistory(customerId);

        // Mark messages from customer as read
        chatService.markAsRead(customerId);

        model.addAttribute("role", "ADMIN");
        model.addAttribute("customerId", customerId);
        model.addAttribute("messages", history);

        return "chat-admin";
    }
}
