package com.honsoft.shopmall.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomFormLoginAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        // Log or add custom behavior
        System.out.println("Access Denied: " + accessDeniedException.getMessage());

        // Option 1: Redirect to access denied page
        response.sendRedirect("/access-denied");

        // Option 2: Send 403 with message (API use case)
        // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // response.getWriter().write("Access Denied: insufficient permissions");
    }
}

