package com.honsoft.shopmall.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.LoginRequest;
import com.honsoft.shopmall.dto.SignupRequest;
import com.honsoft.shopmall.entity.Account;
import com.honsoft.shopmall.entity.AccountRole;
import com.honsoft.shopmall.entity.ERole;
import com.honsoft.shopmall.repository.AccountRepository;
import com.honsoft.shopmall.repository.AccountRoleRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;
	private final JwtService jwtService;
	private final AccountRoleRepository accountRoleRepository;
	private final UserDetailsService userDetailsService;

	public AuthenticationService(@Qualifier("jwtAuthenticationManager") AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, AccountRepository accountRepository,  JwtService jwtService, AccountRoleRepository accountRoleRepository,@Qualifier("customerUserDetailsService") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.accountRoleRepository = accountRoleRepository;
        this.userDetailsService = userDetailsService;
    }

	public String login(LoginRequest loginRequest, HttpServletResponse response) {

		Authentication authenticationRequest = UsernamePasswordAuthenticationToken
				.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

		SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
		List<String> userRoles = authenticationResponse.getAuthorities()
			    .stream()
			    .map(GrantedAuthority::getAuthority)
			    .collect(Collectors.toList());
		jwtService.generateToken(loginRequest.getEmail(),authenticationResponse.getName(),userRoles,response);
		UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();
		return userDetails.getUsername();
	}

	public void registerAccount(SignupRequest signupRequest) {
		if (accountRepository.existsByEmail(signupRequest.getEmail())) {
			throw new EntityExistsException("Email already used");
		}
		// create user object
		Account account = new Account(signupRequest.getNickname(), signupRequest.getEmail(),
				passwordEncoder.encode(signupRequest.getPassword()));
		AccountRole accountRole = accountRoleRepository.findByErole(ERole.ROLE_USER).orElse(null);
		account.setRoles(Collections.singleton(accountRole));
		accountRepository.save(account);

	}

	public void logoutUser(HttpServletResponse response) {
		jwtService.removeTokenFromCookie(response);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = jwtService.getJwtFromCookie(request, JwtName.refreshToken.name());

		if (refreshToken != null && jwtService.validateToken(refreshToken)) {
			String email = jwtService.extractEmail();
			Authentication authentication = authenticateWithRefreshToken(request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwtService.refreshToken(email, response);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		}
	}

	public Authentication authenticateWithRefreshToken(HttpServletRequest request) {
		// 1. Extract refresh token from cookies
		String refreshToken = jwtService.getJwtFromCookie(request, JwtName.refreshToken.name());

		// 2. Validate refresh token
		if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid or expired refresh token");
		}

		// 3. Extract user email from the refresh token
		String email = jwtService.extractEmail();

		// 4. Load user details
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		// 5. Create authenticated Authentication object
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		// 6. Set it in the SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

}