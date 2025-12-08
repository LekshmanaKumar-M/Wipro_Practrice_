package com.myfin.admin.controller;

import com.myfin.admin.service.AdminLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/loans")
public class AdminLoanController {

    @Autowired
    private AdminLoanService loanService;

    @GetMapping
    public String viewLoans(Model model) {
        model.addAttribute("loans", loanService.getAllLoans());
        return "loan-list";
    }

    @GetMapping("/approve/{id}")
    public String approveLoan(@PathVariable Long id) {
        loanService.approveLoan(id);
        return "redirect:/admin/loans";
    }

    @GetMapping("/reject/{id}")
    public String rejectLoan(@PathVariable Long id) {
        loanService.rejectLoan(id);
        return "redirect:/admin/loans";
    }
}
