package com.myfin.admin.controller;

import com.myfin.admin.feign.AdminChatFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminChatController {

    @Autowired
    private AdminChatFeignClient chatClient;

    // List customers with unread chat
    @GetMapping("/admin/chat/unread")
    public String unreadChats(Model model) {
        model.addAttribute("list", chatClient.getUnreadList());
        return "admin-unread-chat-list";
    }

    // List all chat customers
    @GetMapping("/admin/chat/customers")
    public String allChats(Model model) {
        model.addAttribute("list", chatClient.getAllChatCustomers());
        return "admin-all-chat-list";
    }
}
