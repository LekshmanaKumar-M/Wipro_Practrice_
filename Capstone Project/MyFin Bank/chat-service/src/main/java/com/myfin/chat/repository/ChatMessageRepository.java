package com.myfin.chat.repository;

import com.myfin.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByCustomerIdOrderByTimestampAsc(Long customerId);

    Long countByCustomerId(Long customerId);

    Long countByCustomerIdAndReadFlagTrue(Long customerId);

    Long countByCustomerIdAndSenderAndReadFlagFalse(Long customerId, String sender);

    List<ChatMessage> findBySenderAndReadFlagFalse(String sender);
}
