package com.myfin.admin.security;

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

        // ALLOW ADMIN UI PAGES
        if (
        		
        		
        		
        		
        		path.startsWith("/api/admin/login") ||
        		path.startsWith("/api/admin/register")||
                path.startsWith("/admin/login") ||
                path.startsWith("/admin/login-api") ||   
                path.startsWith("/admin/register") ||
                path.startsWith("/admin/register-api") || 
                path.startsWith("/admin/logout") ||
                path.startsWith("/admin/get/")||
                path.startsWith("/admin/dashboard") ||
                path.startsWith("/admin/customers") ||
                path.startsWith("/admin/chat") ||     
                path.startsWith("/admin/chat/") ||
                path.startsWith("/admin/loans")||
                path.contains(".css") ||
                path.contains(".js") ||
                path.contains(".html")
        ) {
            filterChain.doFilter(request, response);
            return;
        }


        // PROTECTED API (JWT REQUIRED)
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
