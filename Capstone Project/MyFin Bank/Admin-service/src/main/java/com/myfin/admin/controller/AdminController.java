package com.myfin.admin.controller;

import com.myfin.admin.model.Admin;
import com.myfin.admin.service.AdminService;
import com.myfin.admin.service.AdminCustomerService;
import com.myfin.admin.security.JwtUtil;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminCustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    // UI REGISTER PAGE
    @GetMapping("/register")
    public String registerPage() {
        return "admin-register";
    }
    
    @GetMapping("/get")
    @ResponseBody
    public List<Admin> getAll() {
        return adminService.getAllAdmins();
    }

    // UI REGISTER FORM SUBMIT
    @PostMapping("/register")
    public String register(Admin admin) {
        adminService.register(admin);
        return "redirect:/admin/login";
    }

    @PostMapping("/register-api")
    @ResponseBody
    public Admin registerApi(@RequestBody Admin admin) {
        return adminService.register(admin);
    }

    
    // UI LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    // UI LOGIN FORM SUBMIT 
    
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        try {
            // 1. Validate Credentials
            Admin admin = adminService.login(username, password);

            // 2. Generate JWT Token
            String token = jwtUtil.generateToken(admin.getUsername());

            // 3. Store admin + token in session
            session.setAttribute("admin", admin);
            session.setAttribute("adminToken", token);

            return "redirect:dashboard";
        }
        catch (Exception e) {
            model.addAttribute("error", "Invalid Username or Password");
            return "admin-login";
        }
    }


    	
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin-dashboard";
    }




    // API LOGIN (JWT)
    @PostMapping("/login-api")
    @ResponseBody
    public Map<String, Object> loginApi(@RequestBody Admin adminData) {

        
            Admin admin = adminService.login(adminData.getUsername(), adminData.getPassword());

            String token = jwtUtil.generateToken(admin.getUsername());

            return Map.of(
                    "message", "Admin Login Successful",
                    "admin", admin.getUsername(),
                    "token", token
            );

        } 
    // Logout ui
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
    
    //Logout
    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

}
