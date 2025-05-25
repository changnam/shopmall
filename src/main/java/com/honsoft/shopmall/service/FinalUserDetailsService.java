package com.honsoft.shopmall.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.UserAuthDto;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.UserRepository;

@Service("finalUserDetailsService")
public class FinalUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public FinalUserDetailsService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found."));

		UserAuthDto userAuthDto = userMapper.toAuthDto(user);

		Set<GrantedAuthority> authorities = userAuthDto.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(userAuthDto.getEmail(), userAuthDto.getPassword(),
				authorities);
	}
}
