package com.myfin.admin.feign;

import com.myfin.admin.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customerClient", url = "http://localhost:8081")
public interface CustomerFeignClient {

    @GetMapping("/api/customer/all")
    List<CustomerDTO> getAllCustomers();

    @GetMapping("/api/customer/get/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);

    @PostMapping("/api/customer/update")
    void updateCustomer(@RequestBody CustomerDTO dto);

    @GetMapping("/api/customer/toggle/{id}")
    void toggleCustomer(@PathVariable Long id);
}
