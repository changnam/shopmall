package com.honsoft.shopmall.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.honsoft.shopmall.exception.TokenExpiredException;
import com.honsoft.shopmall.service.CustomUserDetailsService;
import com.honsoft.shopmall.service.JwtName;
import com.honsoft.shopmall.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private final JwtService jwtService;
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		this.jwtService = jwtService;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = "";
		String jwtName = "";
		if (request.getServletPath().startsWith("/api/v1/auth")) {
			if (request.getServletPath().startsWith("/api/v1/auth/refresh")) {
				jwt = jwtService.getJwtFromCookie(request, JwtName.refreshToken.name());
				jwtName = JwtName.refreshToken.name();
			}
		} else {
			jwt = jwtService.getJwtFromCookie(request, JwtName.accessToken.name());
			jwtName = JwtName.accessToken.name();
		}
		try {
			if (StringUtils.hasText(jwt)) {

				jwtService.validateToken(jwt);
				String userEmail = jwtService.extractEmail();

				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContext context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}

			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException ex) {
			// Delegate to the AuthenticationEntryPoint manually
			SecurityContextHolder.clearContext();
			jwtAuthenticationEntryPoint.commence(request, response, new TokenExpiredException(jwtName));
		}
	}

	private void handleException(HttpServletResponse response, Exception exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.getWriter().println("{\"error\": \"" + exception.getMessage() + "\"}");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
		return !request.getServletPath().startsWith("/api");
	}

	@PostConstruct
	public void logPostMessage() {
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ JwtAuthenticationFilter constructed");
	}
}