package com.honsoft.shopmall.service;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	
	
}
