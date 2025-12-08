package com.myfin.chat.controller;

import com.myfin.chat.model.ChatMessage;
import com.myfin.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private ChatMessageService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void handleChat(@Payload ChatMessage incoming) {

        // Save chat message in DB
        ChatMessage saved = chatService.save(incoming);

        String destination = "/topic/chat/" + saved.getCustomerId();

        // Broadcast to all subscribers (customer + admin view)
        messagingTemplate.convertAndSend(destination, saved);
    }
}
