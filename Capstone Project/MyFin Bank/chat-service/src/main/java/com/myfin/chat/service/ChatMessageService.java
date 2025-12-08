package com.myfin.chat.service;

import com.myfin.chat.model.ChatMessage;
import com.myfin.chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository repo;


    // Save message (admin or customer)
    public ChatMessage save(ChatMessage msg) {
        return repo.save(msg);
    }


    // Chat history for a customer
    public List<ChatMessage> getHistory(Long customerId) {
        return repo.findByCustomerIdOrderByTimestampAsc(customerId);
    }


    // UNREAD FOR ADMIN (messages sent by CUSTOMER only)
    public Long unreadForAdmin(Long customerId) {
        return repo.countByCustomerIdAndSenderAndReadFlagFalse(customerId, "CUSTOMER");
    }


    // LIST OF CUSTOMERS WHO HAVE UNREAD MESSAGES
    public List<Map<String, Object>> getUnreadCustomerList() {

        List<ChatMessage> unreadMsgs = repo.findBySenderAndReadFlagFalse("CUSTOMER");

        Map<Long, Long> grouped = unreadMsgs.stream()
                .collect(Collectors.groupingBy(ChatMessage::getCustomerId, Collectors.counting()));

        return grouped.entrySet().stream()
                .map(e -> Map.<String, Object>of(
                        "customerId", e.getKey(),
                        "unread", e.getValue()
                ))
                .collect(Collectors.toList());
    }


    // LIST OF ALL CUSTOMERS WHO HAVE CHATTED AT LEAST ONCE
    public List<Map<String, Object>> getAllChatCustomers() {

        List<ChatMessage> all = repo.findAll();

        Set<Long> customerIds = all.stream()
                .map(ChatMessage::getCustomerId)
                .collect(Collectors.toSet());

        List<Map<String, Object>> result = new ArrayList<>();

        for (Long cid : customerIds) {
            Long unread = unreadForAdmin(cid);

            result.add(
                    Map.<String, Object>of(
                            "customerId", cid,
                            "unread", unread
                    )
            );
        }

        return result;
    }


    // MARK ALL CUSTOMER → ADMIN MESSAGES AS READ
    public void markAsRead(Long customerId) {
        List<ChatMessage> unread = repo.findBySenderAndReadFlagFalse("CUSTOMER");

        unread.stream()
                .filter(m -> m.getCustomerId().equals(customerId))
                .forEach(m -> m.setReadFlag(true));

        repo.saveAll(unread);
    }


    // STATS: total, unread, read
    public Map<String, Long> getStats(Long customerId) {

        Long total = repo.countByCustomerId(customerId);
        Long unread = unreadForAdmin(customerId);
        Long read = repo.countByCustomerIdAndReadFlagTrue(customerId);

        return Map.of(
                "total", total,
                "unread", unread,
                "read", read
        );
    }
    
    
    
   
    public void saveMessage(Long customerId, String sender, String message) {

        ChatMessage chat = new ChatMessage();
        chat.setCustomerId(customerId);
        chat.setSender(sender);
        chat.setMessage(message);
        chat.setTimestamp(LocalDateTime.now());

        repo.save(chat);  // ✅ Correct field name
    }


}
