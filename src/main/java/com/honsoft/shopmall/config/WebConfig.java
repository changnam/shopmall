package com.honsoft.shopmall.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.honsoft.shopmall.interceptor.MonitoringInterceptor;
import com.honsoft.shopmall.util.DelegatingLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.uploadDir}")
	String fileDir;
	
//	@Value("${server.servlet.context-path:}")
//	private String contextPath; 
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		System.out.println("=======================> "+contextPath+"/files/**");
		registry.addResourceHandler("/webjars/*").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/files/**").addResourceLocations("file:///"+fileDir);
//		.setCachePeriod(60*60*24*365);
		//"file:/upload/files/" <-- 리눅스의 경우, / 로 끝나야 함
	}
	
//	@Bean
//    public LocaleResolver localeResolver() {
//        return new CookieLocaleResolver();
//    }
	
	@Bean
    public SessionLocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREAN);
        return resolver;
    }
	
	@Bean
    public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public LocaleResolver localeResolver(@Qualifier("sessionLocaleResolver") SessionLocaleResolver sessionLocaleResolver,@Qualifier("acceptHeaderLocaleResolver") AcceptHeaderLocaleResolver acceptHeaderLocaleResolver ) {
        return new DelegatingLocaleResolver(sessionLocaleResolver, acceptHeaderLocaleResolver);
    }
    
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(new MonitoringInterceptor());
    }
    
    @Bean
    public MessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    	messageSource.setBasename("messages");
    	messageSource.setDefaultEncoding("UTF-8");
    	messageSource.setFallbackToSystemLocale(false);
    	messageSource.setAlwaysUseMessageFormat(false);
    	return messageSource;
    }
    
    //반드시 validator 이름으로 등록해야함. 
    @Bean
    public LocalValidatorFactoryBean validator(@Qualifier("messageSource") MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
    
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    
}
