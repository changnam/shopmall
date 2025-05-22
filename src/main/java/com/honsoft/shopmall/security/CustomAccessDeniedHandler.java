package com.honsoft.shopmall.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String logMessage = String.format("user: %s, authorities: %s, %s for %s",
				auth != null ? auth.getName() : "anonymous", auth != null ? auth.getAuthorities() : "none",
				ex.getMessage(), request.getRequestURI());

//		logger.info("user: {}, authorities: {}, {} for {}",auth.getName(),auth.getAuthorities(),ex.getMessage(),request.getRequestURI());
		logger.info(logMessage);
		if (!response.isCommitted()) {
			Map<String, Object> body = new HashMap<>();
			body.put("message", logMessage);
			body.put("error", "ACCESS_DENIED");
			body.put("data", null);

			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(body));

		}
	}
}
