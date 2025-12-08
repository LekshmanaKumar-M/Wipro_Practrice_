package com.myfin.admin.service;

import com.myfin.admin.dto.CustomerDTO;
import com.myfin.admin.feign.CustomerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCustomerService {

    @Autowired
    private CustomerFeignClient client;

    public List<CustomerDTO> getAllCustomers() {
        return client.getAllCustomers();
    }

    public CustomerDTO getCustomerById(Long id) {
        return client.getCustomerById(id);
    }

    public void updateCustomer(CustomerDTO customer) {
        client.updateCustomer(customer);
    }

    public void toggleActive(Long id) {
        client.toggleCustomer(id);
    }
}
