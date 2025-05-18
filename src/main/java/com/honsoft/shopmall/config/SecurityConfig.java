package com.honsoft.shopmall.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.honsoft.shopmall.security.CustomAuthenticationFailureHandler;
import com.honsoft.shopmall.security.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.honsoft.shopmall.security.JwtAuthenticationEntryPoint;
import com.honsoft.shopmall.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
//@AllArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
			,CustomSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler,CustomAuthenticationFailureHandler authenticationFailureHandler) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
	}
	
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
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // <--- here
                )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
//        http.formLogin(formLogin -> formLogin.disable());
        http.logout(logout -> logout.disable());

        return http.build();
     }

    @Bean
    @Order(2)
    public SecurityFilterChain formLoginSecurityFilterChain(HttpSecurity http, @Qualifier("formAuthenticationManager") AuthenticationManager authenticationManager, @Qualifier("memberUserDetailsService") UserDetailsService userDetailsService) throws Exception {
    	
    	http.csrf(csrf -> csrf.disable());
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/home","/books","/members/add","/login").permitAll()
                .anyRequest().authenticated()
            ).userDetailsService(userDetailsService)
            .authenticationManager(authenticationManager)
//            .formLogin(Customizer.withDefaults());
//            .formLogin(form -> form.loginPage("/login").permitAll());
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(authenticationFailureHandler)  // <--- here
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );

        return http.build();
    }

    @Bean("jwtAuthenticationManager")
    @Primary
    public AuthenticationManager jwtAuthenticationManager(@Qualifier("customerUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
    
    @Bean("formAuthenticationManager")
    public AuthenticationManager formAuthenticationManager(@Qualifier("memberUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
    
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//    	return config.getAuthenticationManager();
//    }
//	
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
    
//    @Bean
//    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
//        return (request, response, authException) -> {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\": \"Unauthorized access\"}");
//        };
//    }
    
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return (request, response, exception) -> {
//            response.sendRedirect("/login?error=true");
//        };
//    }
//    

	@Bean
	public WebSecurityCustomizer webSecurity() {

		return (web) -> web.ignoring().requestMatchers("/css/**", "/img/**", "/js/**", "/webjars/**");
	}

}
