package com.honsoft.shopmall.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

import com.honsoft.shopmall.security.CustomAccessDeniedHandler;
import com.honsoft.shopmall.security.CustomAuthenticationFailureHandler;
import com.honsoft.shopmall.security.CustomFormLoginAccessDeniedHandler;
import com.honsoft.shopmall.security.CustomLoginUrlEntryPoint;
import com.honsoft.shopmall.security.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.honsoft.shopmall.security.JwtAuthenticationEntryPoint;
import com.honsoft.shopmall.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
//@AllArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;
	private final CustomAccessDeniedHandler accessDeniedHandler;
//	private final CustomLoginUrlEntryPoint customLoginUrlEntryPoint;
	private final CustomFormLoginAccessDeniedHandler formLoginAccessDeniedHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			CustomSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler,
			CustomAuthenticationFailureHandler authenticationFailureHandler,
			CustomAccessDeniedHandler accessDeniedHandler, 
//			CustomLoginUrlEntryPoint customLoginUrlEntryPoint,
			CustomFormLoginAccessDeniedHandler formLoginAccessDeniedHandler) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.accessDeniedHandler = accessDeniedHandler;
//		this.customLoginUrlEntryPoint = customLoginUrlEntryPoint;
		this.formLoginAccessDeniedHandler = formLoginAccessDeniedHandler;
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
		http.securityMatcher("/api/**").cors(cors -> {
		}).csrf(csrf -> csrf.disable()).formLogin(formLogin -> formLogin.disable())
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authz -> authz.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/accounts").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/accounts").hasRole("ADMIN").anyRequest()
						.authenticated())
				.anonymous(anonymous -> anonymous.disable())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint) // handles 401
						.accessDeniedHandler(accessDeniedHandler) // handles 403
				).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//        http.formLogin(formLogin -> formLogin.disable());
		http.logout(logout -> logout.disable());

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain formLoginSecurityFilterChain(HttpSecurity http,
			@Qualifier("formAuthenticationManager") AuthenticationManager authenticationManager,
			@Qualifier("memberUserDetailsService") UserDetailsService userDetailsService) throws Exception {

		http.csrf(csrf -> csrf.disable());
//		http.anonymous(anonymous -> anonymous.disable());
		http.authorizeHttpRequests(
				authz -> authz.requestMatchers("/home", "/books", "/members/add", "/login", "/access-denied")
						.permitAll().requestMatchers(HttpMethod.POST, "/accounts").permitAll()
						.requestMatchers(HttpMethod.GET, "/accounts").hasRole("ADMIN").anyRequest().authenticated())
				.userDetailsService(userDetailsService).authenticationManager(authenticationManager)
//            .formLogin(Customizer.withDefaults());
//            .formLogin(form -> form.loginPage("/login").permitAll());
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
						.failureHandler(authenticationFailureHandler) // <--- here
						.successHandler(authenticationSuccessHandler).permitAll())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomLoginUrlEntryPoint("/login"))
						.accessDeniedHandler(formLoginAccessDeniedHandler))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout"));

		return http.build();
	}

	@Bean("jwtAuthenticationManager")
	@Primary
	public AuthenticationManager jwtAuthenticationManager(
			@Qualifier("customerUserDetailsService") UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean("formAuthenticationManager")
	public AuthenticationManager formAuthenticationManager(
			@Qualifier("memberUserDetailsService") UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
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
