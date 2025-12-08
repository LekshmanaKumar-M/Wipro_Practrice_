package com.myfin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myfin.model.Customer;
import com.myfin.model.Loan;
import com.myfin.security.JwtUtil;
import com.myfin.service.CustomerService;
import com.myfin.service.InvestmentService;
import com.myfin.service.LoanService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private LoanService loanService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private JwtUtil jwtUtil;

    
    //REGISTER
  
    @PostMapping("/register-api")
    @ResponseBody
    public Customer registerApi(@RequestBody Customer c) {
        return service.register(c);
    }

    @PostMapping("/register")
    public String registerForm(Customer c) {
        service.register(c);
        return "redirect:/login";
    }

    
    // LOGIN
   


   
    @PostMapping("/login")
    public String login(@RequestParam String accountNumber,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        try {

            // 1. Validate User
            Customer customer = service.login(accountNumber, password);

            // 2. Generate JWT Token
            String token = jwtUtil.generateToken(customer.getEmail());

            // 3. Store in session
            session.setAttribute("customer", customer);
            session.setAttribute("token", token);

            // 4. Go to welcome page
            model.addAttribute("customer", customer);
            return "welcome";
        }
        catch (Exception e) {
            // Show error on same login page
            model.addAttribute("error", "Invalid account number or password");
            return "login";  // MUST match your login.html name
        }
    }



    @PostMapping("/login-api-jwt")
    @ResponseBody
    public String loginApiJwt(@RequestBody Customer customerData) {

        Customer customer = service.login(customerData.getAccountNumber(), customerData.getPassword());
        String token = jwtUtil.generateToken(customer.getAccountNumber());

        return "Token: " + token;
    }

    
    //  ADMIN CUSTOMER MANAGEMENT
    
    @GetMapping("/all")
    @ResponseBody
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Customer getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id);
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCustomer(@RequestBody Customer dto) {

        Customer c = service.getCustomerById(dto.getId());

        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setAccountNumber(dto.getAccountNumber());
        c.setBalance(dto.getBalance());
        c.setActive(dto.isActive());

        service.register(c);
        return "Customer updated";
    }

    @GetMapping("/toggle/{id}")
    @ResponseBody
    public String toggleActive(@PathVariable Long id) {

        Customer c = service.getCustomerById(id);
        c.setActive(!c.isActive());
        service.register(c);

        return "Status changed";
    }
 
    // DEPOSIT (UI)
    
    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount, HttpSession session, Model model) {

        Customer cust = (Customer) session.getAttribute("customer");

        try {
            double bal = service.deposit(cust, amount);
            String tx = service.getLastTransactionId();

            model.addAttribute("message", "Deposit Successful | TX: " + tx);
            model.addAttribute("txid", tx);

            return "result";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "deposit";
        }
    }

    // API
    @PostMapping("/deposit-api")
    @ResponseBody
    public String depositApi(@RequestParam double amount,
                             @RequestParam String accountNumber) {

        Customer cust = service.getCustomerByAccountNumber(accountNumber);
        double bal = service.deposit(cust, amount);
        String tx = service.getLastTransactionId();

        return "{ \"message\": \"Deposit successful\", \"balance\": " + bal + ", \"txId\": \"" + tx + "\" }";
    }

   
    // WITHDRAW (UI)
    
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount, HttpSession session, Model model) {

        Customer cust = (Customer) session.getAttribute("customer");

        try {
            double bal = service.withdraw(cust, amount);
            String tx = service.getLastTransactionId();

            model.addAttribute("message", "Withdraw Successful | TX: " + tx);
            model.addAttribute("txid", tx);

            return "result";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "withdraw";
        }
    }

    // API
    @PostMapping("/withdraw-api")
    @ResponseBody
    public String withdrawApi(@RequestParam String accountNumber,
                              @RequestParam double amount) {

        Customer c = service.getCustomerByAccountNumber(accountNumber);
        double bal = service.withdraw(c, amount);
        String tx = service.getLastTransactionId();

        return "{ \"message\": \"Withdraw successful\", \"balance\": " + bal + ", \"txId\": \"" + tx + "\" }";
    }

 
    // TRANSFER (UI)
    
    @PostMapping("/transfer")
    public String transfer(@RequestParam String toAccount,
                           @RequestParam double amount,
                           HttpSession session,
                           Model model) {

        Customer sender = (Customer) session.getAttribute("customer");

        try {
            String msg = service.transferByAccountNumber(sender, toAccount, amount);
            String tx = service.getLastTransactionId();

            model.addAttribute("message", msg + " | TX: " + tx);
            model.addAttribute("txid", tx);

            return "result";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "transfer";
        }
    }

    // API
    @PostMapping("/transfer-api")
    @ResponseBody
    public String transferApi(@RequestParam String fromAccount,
                              @RequestParam String toAccount,
                              @RequestParam double amount) {

        Customer sender = service.getCustomerByAccountNumber(fromAccount);

        String msg = service.transferByAccountNumber(sender, toAccount, amount);
        String tx = service.getLastTransactionId();

        return "{ \"message\": \"" + msg + "\", \"txId\": \"" + tx + "\" }";
    }

    
    //APPLY LOAN (UI)
    
    @PostMapping("/apply-loan")
    public String applyLoan(@RequestParam double amount,
                            @RequestParam String loanType,
                            @RequestParam String purpose,
                            HttpSession session,
                            Model model) {

        Customer cust = (Customer) session.getAttribute("customer");

        try {
            Loan loan = loanService.applyLoan(cust.getAccountNumber(), amount, loanType, purpose);

            model.addAttribute("message", "Loan Applied Successfully! Loan ID: " + loan.getId());
            model.addAttribute("txid", loan.getId());

            return "result";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "apply-loan";
        }
    }

    
    //LOAN STATUS
    
    @GetMapping("/loan-status")
    public String loanStatusPage(HttpSession session, Model model) {

        Customer cust = (Customer) session.getAttribute("customer");

        if (cust == null)
            return "redirect:/login";

        model.addAttribute("loans", loanService.getLoans(cust.getAccountNumber()));
        return "loan-status";
    }

    
    //INVESTMENT TRANSFER (UI)
    
    @PostMapping("/transfer-investment")
    public String transferToInvestment(@RequestParam String type,
                                       @RequestParam double amount,
                                       HttpSession session,
                                       Model model) {

        Customer c = (Customer) session.getAttribute("customer");

        try {
            String tx = "TXN-" + System.currentTimeMillis();
            String msg = investmentService.transfer(c.getAccountNumber(), type, amount);

            Customer updated = service.login(c.getAccountNumber(), c.getPassword());
            session.setAttribute("customer", updated);

            model.addAttribute("message", msg + " | TX: " + tx);
            model.addAttribute("txid", tx);

            return "result";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "transfer-investment";
        }
    }

   
    //EMI + RD + FD CALCULATORS
 
    @GetMapping("/calculate-emi")
    public String showEmiPage() {
        return "emi-calculator";
    }

    @PostMapping("/calculate-emi")
    public String calculateEmi(@RequestParam double amount,
                               @RequestParam double rate,
                               @RequestParam int months,
                               Model model) {

        double emi = loanService.calculateEMI(amount, rate, months);
        model.addAttribute("emi", emi);

        return "emi-calculator";
    }

    @GetMapping("/investment-calculator")
    public String showInvestmentCalcPage() {
        return "invest-calculator";
    }

    @PostMapping("/calc-rd")
    public String calcRD(@RequestParam double deposit,
                         @RequestParam double rate,
                         @RequestParam int months,
                         Model model) {

        double result = investmentService.calculateRD(deposit, rate, months);
        model.addAttribute("rd", result);

        return "invest-calculator";
    }

    @PostMapping("/calc-fd")
    public String calcFD(@RequestParam double principal,
                         @RequestParam double rate,
                         @RequestParam double years,
                         Model model) {

        double result = investmentService.calculateFD(principal, rate, years);
        model.addAttribute("fd", result);

        return "invest-calculator";
    }
}
