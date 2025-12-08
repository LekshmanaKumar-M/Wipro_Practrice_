package com.myfin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.myfin.model.Customer;

@Controller
public class UIController {

    @GetMapping("/")
    public String index() {
    	return "index";
    	}

    @GetMapping("/login")
    public String login() { 
    	return "login"; 
    	}

    @GetMapping("/register")
    public String register() { 
    	return "register"; 
    	}

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {

        Customer c = (Customer) session.getAttribute("customer");
        if (c == null) return "redirect:/login";

        model.addAttribute("customer", c);
        return "welcome";
    }

    @GetMapping("/deposit")
    public String deposit() { return "deposit"; }

    @GetMapping("/withdraw")
    public String withdraw() { return "withdraw"; }

    @GetMapping("/transfer")
    public String transfer() { return "transfer"; }

    @GetMapping("/apply-loan")
    public String applyLoan(HttpSession session) {

        if (session.getAttribute("customer") == null)
            return "redirect:/login";

        return "loan-apply";
    }

    @GetMapping("/loan-status")
    public String loanStatus() { return "redirect:/api/customer/loan-status"; }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/transfer-investment")
    public String transferInvestmentPage(HttpSession session) {

        if (session.getAttribute("customer") == null)
            return "redirect:/login";

        return "transfer-investment";
    }
    
    @GetMapping("/investment-calculator")
    public String showInvestmentCalcPage() {
        return "invest-calculator";
    }



}
