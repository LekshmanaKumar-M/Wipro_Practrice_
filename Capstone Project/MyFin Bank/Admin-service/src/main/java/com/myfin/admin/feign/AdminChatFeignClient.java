package com.myfin.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "chatService", url = "http://localhost:8084")
public interface AdminChatFeignClient {

    @GetMapping("/api/chat/admin/unread-list")
    List<Map<String, Object>> getUnreadList();

    @GetMapping("/api/chat/admin/customers")
    List<Map<String, Object>> getAllChatCustomers();

    @GetMapping("/api/chat/admin/stats/{customerId}")
    Map<String, Long> getStats(@PathVariable Long customerId);

    @PutMapping("/api/chat/admin/mark-read/{customerId}")
    String markRead(@PathVariable Long customerId);
}
