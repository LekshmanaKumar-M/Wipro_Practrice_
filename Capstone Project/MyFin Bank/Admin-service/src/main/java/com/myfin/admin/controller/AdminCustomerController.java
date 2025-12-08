package com.myfin.admin.controller;

import com.myfin.admin.dto.CustomerDTO;
import com.myfin.admin.service.AdminCustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

    @Autowired
    private AdminCustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer-list";
    }
    
    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer-edit";
    }
    
    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/update")
    public String updateCustomer(CustomerDTO dto) {
        customerService.updateCustomer(dto);
        return "redirect:/admin/customers/list";
    }
    
    
    // update by admin
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        dto.setId(id);
        customerService.updateCustomer(dto);
        return "Updated Successfully";
    }

    @GetMapping("/toggle/{id}")
    public String toggleCustomer(@PathVariable Long id) {
        customerService.toggleActive(id);
        return "redirect:/admin/customers/list";
    }
    
    @PutMapping("/toggle/{id}")
    public ResponseEntity<String> toggleCustomer1(@PathVariable Long id) {
        customerService.toggleActive(id);
        return ResponseEntity.ok("Status updated");
    }

}
