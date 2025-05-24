package com.honsoft.shopmall.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.repository.UserRepository;

@Service("finalUserDetailsService")
public class FinalUserDetailsService implements UserDetailsService {

    private  UserRepository userRepository;
    public FinalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	User user = userRepository.findByEmail(email)
    	        .orElseThrow(() -> new UsernameNotFoundException(email + " not found."));

        Set<GrantedAuthority> authorities = user
                .getUserRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRole().getRoleName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
        		user.getEmail(),
        		user.getPassword(),
                authorities
        );
    }
}
