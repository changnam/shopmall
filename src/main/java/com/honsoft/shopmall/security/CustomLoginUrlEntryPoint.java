package com.honsoft.shopmall.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component("CustomLoginUrlEntryPoint")
public class CustomLoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(CustomLoginUrlEntryPoint.class);
	
    public CustomLoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Log additional information
        String requestedUri = request.getRequestURI();
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

       logger.info("Unauthorized access attempt for Requested URI: {}, Client IP: {}, User-Agent: {}",requestedUri, clientIp, userAgent);
//        System.out.println("Requested URI: " + requestedUri);
//        System.out.println("Client IP: " + clientIp);
//        System.out.println("User-Agent: " + userAgent);

        // Continue with default behavior (redirect to login page)
        super.commence(request, response, authException);
    }
}
