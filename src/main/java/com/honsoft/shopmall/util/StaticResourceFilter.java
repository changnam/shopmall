package com.honsoft.shopmall.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StaticResourceFilter extends OncePerRequestFilter {
	private static Logger logger = LoggerFactory.getLogger(StaticResourceFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        logger.info("request uri: "+uri);
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/") || uri.startsWith("/webjars/")) {
            logger.info("📦 Static resource requested: " + uri);
        }

        filterChain.doFilter(request, response);
    }
}

