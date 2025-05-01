package com.honsoft.shopmall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.honsoft.shopmall.util.SessionLoggerFilter;
import com.honsoft.shopmall.util.StaticResourceFilter;

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

	@Bean
	public FilterRegistrationBean<StaticResourceFilter> loggingStaticFilter() {
		FilterRegistrationBean<StaticResourceFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new StaticResourceFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE -1);

		return registrationBean;
	}
	
//	@Bean
//    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
//        return new ServletListenerRegistrationBean<>(new SessionLogger());
//    }
}
