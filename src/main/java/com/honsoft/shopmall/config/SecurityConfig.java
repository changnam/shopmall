package com.honsoft.shopmall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.honsoft.shopmall.util.CustomHttpSessionListener;
import com.honsoft.shopmall.util.SessionLoggerFilter;

import jakarta.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

//	@Value("${server.servlet.context-path:}")
//	private String contextPath;

	@Bean
	public SecurityFilterChain webSecurityConfig(HttpSecurity http) throws Exception {
//		String protectedPath = contextPath + "/**";
		http.securityMatcher("/**");
		http.csrf((csrf) -> csrf.disable());
		http.authorizeHttpRequests((auth) -> auth.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/manager/**").hasRole("MANAGER")
				.requestMatchers("/member/**").hasAnyRole("USER","ADMIN")
				.requestMatchers("/books/**").authenticated()
				.anyRequest().permitAll());
		http.formLogin(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurity() {

		return (web) -> web.ignoring().requestMatchers("/css/**", "/img/**",
				"/js/**", "/webjars/**");
	}

	@Bean
	public SecurityFilterChain apiSecurityConfig(HttpSecurity http) throws Exception {
//		String protectedPath = contextPath + "/api/**";
		http.securityMatcher("/api/**");
		http.csrf((csrf) -> csrf.disable());

		return http.build();
	}
	
	
	@Bean
	public UserDetailsService user(PasswordEncoder passwordEncoder) {
	UserDetails user = User.builder()
			.username("user")
			.password(passwordEncoder.encode("pass"))
			.roles("USER")
			.build();
		UserDetails admin = User.builder()
			.username("admin")
			.password(passwordEncoder.encode("pass"))
			.roles("ADMIN")
			.build();
		UserDetails manager = User.builder()
				.username("manager")
				.password(passwordEncoder.encode("pass"))
				.roles("MANAGER")
				.build();
		return new InMemoryUserDetailsManager(user, admin, manager);
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
