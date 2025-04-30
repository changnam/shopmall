package com.honsoft.shopmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Value("${server.servlet.context-path:}")
	private String contextPath; 
	
	@Bean
	public SecurityFilterChain webSecurityConfig(HttpSecurity http) throws Exception {
		String protectedPath = contextPath + "/**";
		http.securityMatcher(protectedPath);
		http.csrf((csrf) -> csrf.disable());
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurity() {
		
		return (web) -> web.ignoring().requestMatchers(contextPath+"/css/**",contextPath+"/img/**",contextPath+"/js/**",contextPath+"/webjars/**");
	}
	
	@Bean
	public SecurityFilterChain apiSecurityConfig(HttpSecurity http) throws Exception {
		String protectedPath = contextPath + "/api/**";
		http.securityMatcher(protectedPath);
		http.csrf((csrf) -> csrf.disable());
		
		return http.build();
	}

}
