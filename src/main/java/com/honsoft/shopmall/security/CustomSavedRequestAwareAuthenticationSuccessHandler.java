package com.honsoft.shopmall.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public CustomSavedRequestAwareAuthenticationSuccessHandler() {
        // Set default target URL if no saved request is present
        setDefaultTargetUrl("/home");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws ServletException, IOException {
        // Custom logic (e.g., logging)
//        System.out.println(">>> Login successful for: " + authentication.getName());

        // Continue with default behavior (redirect to saved request or default target URL)
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
