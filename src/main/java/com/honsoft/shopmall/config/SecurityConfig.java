package com.honsoft.shopmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class SecurityConfig {

//	@Bean
//	protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//		//return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        
//    }

	@Bean(name = "myPasswordEncoder")
	public PasswordEncoder getPasswordEncoder() {
		DelegatingPasswordEncoder delPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories
				.createDelegatingPasswordEncoder();
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
		return delPasswordEncoder;
	}

	/*
	 * @Bean protected UserDetailsService users() { UserDetails admin =
	 * User.builder() .username("Admin")
	 * .password(passwordEncoder().encode("Admin1234")) .roles("ADMIN") .build();
	 * return new InMemoryUserDetailsManager(admin); }
	 */

	/*
	 * @Bean public AuthenticationManager
	 * authenticationManager(AuthenticationConfiguration
	 * authenticationConfiguration) throws Exception { return
	 * authenticationConfiguration.getAuthenticationManager(); }
	 * 
	 */

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)

				// 특정 URL에 대한 권한 설정.
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/books/add")
						.hasRole("ADMIN").requestMatchers("/orders/list").hasRole("ADMIN")
						// .requestMatchers("/members").hasRole("USER" )
						// .requestMatchers("/order/**").hasAnyRole("USER", "ADMIN" )
						.anyRequest().permitAll())
				// .formLogin(Customizer.withDefaults());
				.formLogin(formLogin -> formLogin

						.loginPage("/login") // 사용자 정의 로그인 페이지
						.loginProcessingUrl("/login").defaultSuccessUrl("/books/add")// 로그인 성공 후 이동 페이지
						.defaultSuccessUrl("/orders/list")// 로그인 성공 후 이동 페이지
						.defaultSuccessUrl("/").failureUrl("/loginfailed") // 로그인 실패 후 이동 페이지
						.usernameParameter("username")
						// .usernameParameter("email")
						.passwordParameter("password")

				)

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login")
				// .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
				// .invalidateHttpSession(true) // 로그아웃 시 세션 종료
				// .clearAuthentication(true) // 로그아웃 시 권한 제거
				);

		return http.build();

	}
	
//	List<Permission> permissions = permissionRepository.findAll();
//
//    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorize = http
//        .csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(authz -> {
//            for (Permission permission : permissions) {
//                authz
//                    .requestMatchers(HttpMethod.valueOf(permission.getHttpMethod().toUpperCase()), permission.getPath())
//                    .hasRole(permission.getRole().getName());
//            }
//
//            authz
//                .anyRequest().authenticated();
//        });
//
//    http
//        .userDetailsService(userDetailsService)
//        .formLogin(withDefaults());
//
//    return http.build();
    

	@Bean
	public WebSecurityCustomizer webSecurity() {

		return (web) -> web.ignoring().requestMatchers("/css/**", "/img/**", "/js/**", "/webjars/**");
	}

}
