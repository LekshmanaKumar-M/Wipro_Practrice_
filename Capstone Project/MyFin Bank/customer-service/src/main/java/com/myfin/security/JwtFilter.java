package com.myfin.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ---------- ALLOW UI PAGES ----------
        if (path.equals("/") ||
            path.startsWith("/login") ||
            path.startsWith("/register") ||
            path.startsWith("/welcome") ||
            path.startsWith("/deposit") ||
            path.startsWith("/withdraw") ||
            path.startsWith("/transfer") ||
            path.startsWith("/apply-loan") ||
            path.startsWith("/loan-status") ||
            path.startsWith("/transfer-investment") ||
            path.startsWith("/emi-calculator") ||
            path.startsWith("/investment-calculator") ||   
            path.contains(".html") ||
            path.contains(".css") ||
            path.contains(".js")) {

            filterChain.doFilter(request, response);
            return;
        }

        // ---------- ALLOW CUSTOMER PUBLIC APIs ----------
        if (path.startsWith("/api/customer/login") ||
            path.startsWith("/api/customer/register") ||
            path.startsWith("/api/customer/login-api-jwt") ||
            path.startsWith("/api/customer/deposit-api") ||
            path.startsWith("/api/customer/withdraw-api") ||
            path.startsWith("/api/customer/transfer-api") ||
            path.startsWith("/api/customer/deposit") ||
            path.startsWith("/api/customer/withdraw") ||
            path.startsWith("/api/customer/transfer") ||
            path.startsWith("/api/customer/apply-loan") ||
            path.startsWith("/api/customer/loan-status") ||
            path.startsWith("/api/customer/transfer-investment") ||
            path.startsWith("/api/customer/calculate-emi") ||     
            path.startsWith("/api/customer/calc-rd") ||            
            path.startsWith("/api/customer/calc-fd")) {            

            filterChain.doFilter(request, response);
            return;
        }

        // ---------- FEIGN CLIENT (ADMIN SERVICE) ----------
        if (path.startsWith("/api/customer/toggle") ||
            path.startsWith("/api/customer/update") ||
            path.startsWith("/api/customer/get") ||
            path.startsWith("/api/customer/all") ||
            path.startsWith("/api/loans")) {

            filterChain.doFilter(request, response);
            return;
        }
        
        

        // ---------- JWT CHECK ----------
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(401);
        response.getWriter().write("Invalid or Missing JWT Token");
    }
}
