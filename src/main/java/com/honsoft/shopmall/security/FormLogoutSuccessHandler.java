package com.honsoft.shopmall.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.honsoft.shopmall.service.JwtName;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import java.io.IOException;

public class FormLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    public FormLogoutSuccessHandler() {
        // Optional: set default target URL after logout
        setDefaultTargetUrl("/login?logout");
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Custom logic before redirect or response
        // For example, clear a JWT cookie:
        Cookie cookie = new Cookie(JwtName.accessToken.name(), null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);  // delete cookie immediately
        response.addCookie(cookie);

        // Call parent to perform redirect or default behavior
        super.onLogoutSuccess(request, response, authentication);
    }
}
