package com.honsoft.shopmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.honsoft.shopmall.security.JwtAuthenticationFilter;
import com.honsoft.shopmall.service.CustomUserDetailsService;
import com.honsoft.shopmall.service.JwtService;
import com.honsoft.shopmall.util.SessionLoggerFilter;
import com.honsoft.shopmall.util.StaticResourceFilter;

@Configuration
public class ServletFilterConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ServletFilterConfig.class);
//	
//	private final JwtService jwtService;
//    private final CustomUserDetailsService customUserDetailsService;
//    
//    public ServletFilterConfig(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
//        this.jwtService = jwtService;
//        this.customUserDetailsService = customUserDetailsService;
//    }
//    
    
	@Bean
	public FilterRegistrationBean<SessionLoggerFilter> loggingFilter() {
		FilterRegistrationBean<SessionLoggerFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new SessionLoggerFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<StaticResourceFilter> loggingStaticFilter() {
		
		FilterRegistrationBean<StaticResourceFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new StaticResourceFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE -1);

		return registrationBean;
	}
	
//	
//	@Bean
//	public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthFilter() {
//		logger.info("===================> start jwtAuthFilter");
//		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//
//		registrationBean.setFilter(new JwtAuthenticationFilter(jwtService,customUserDetailsService));
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE -2);
//
//		logger.info("===================> end jwtAuthFilter");
//		return registrationBean;
//	}
//	
//	
//	@Bean
//    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
//        return new ServletListenerRegistrationBean<>(new SessionLogger());
//    }
}
