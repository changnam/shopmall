package com.honsoft.shopmall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.honsoft.shopmall.util.SessionLoggerFilter;

@Configuration
public class ServletFilterConfig {
	@Bean
	public FilterRegistrationBean<SessionLoggerFilter> loggingFilter() {
		FilterRegistrationBean<SessionLoggerFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new SessionLoggerFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return registrationBean;
	}

//	@Bean
//    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
//        return new ServletListenerRegistrationBean<>(new SessionLogger());
//    }
}
