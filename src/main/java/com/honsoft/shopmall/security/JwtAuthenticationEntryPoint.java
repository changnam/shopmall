package com.honsoft.shopmall.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	 private final ObjectMapper objectMapper = new ObjectMapper();
	 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ spring security authorization error ");
		// Log the reason (optional)
//        System.out.println("Unauthorized request: " + authException.getMessage());

		// Send a 401 response
		if (!response.isCommitted()) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.setContentType("application/json");
//			response.getWriter()
//					.write("{\"error\": \"Unauthorized access\",\"message\": \"Unauthorized access\",\"data\":}");
//		
			HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
	        String message = "Unauthorized access";

	        Map<String, Object> body = new HashMap<>();
	        body.put("message", message);
	        body.put("httpStatus", httpStatus.value()); // or .value()
	        body.put("error", "error");
	        body.put("data", null);

	        response.setStatus(httpStatus.value());
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write(objectMapper.writeValueAsString(body));
	        System.out.println("respond: "+objectMapper.writeValueAsString(body));
		}
	}
}
