package com.honsoft.shopmall.util;

import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@Component
public class SessionLoggerFilter extends OncePerRequestFilter {
	private static Logger logger = LoggerFactory.getLogger(SessionLoggerFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Session ID: " + session.getId());
            logger.info("isNew: " + session.isNew());
            logger.info("attributes: " + session.getAttributeNames());
        } else {
            logger.info("No session for this request.");
        }

        filterChain.doFilter(request, response);
    }
}

