package com.honsoft.shopmall.service;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AccountRoleRepository accountRoleRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, AccountRepository accountRepository,  JwtService jwtService, AccountRoleRepository accountRoleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.accountRoleRepository = accountRoleRepository;
    }
    public String login(LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        jwtService.generateToken(loginRequest.getEmail(), response);
        UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();
        return userDetails.getUsername();
    }
    public void registerAccount(SignupRequest signupRequest){
        if(accountRepository.existsByEmail(signupRequest.getEmail())){
            throw new EntityExistsException("Email already used");
        }
        // create user object
        Account account = new Account(signupRequest.getName(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
        AccountRole accountRole = accountRoleRepository.findByErole(ERole.ROLE_USER).orElse(null);
        account.setRoles(Collections.singleton(accountRole));
        accountRepository.save(account);

    }
    public void logoutUser(HttpServletResponse response){
        jwtService.removeTokenFromCookie(response);
    }
}