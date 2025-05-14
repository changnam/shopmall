package com.honsoft.shopmall.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

//	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		User savedUser = userRepository.save(user);
		UserDto savedUserDto = userMapper.toDto(savedUser);
		return savedUserDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> {
			return userMapper.toDto(user);
		}).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserById(String userId) {
		// TODO Auto-generated method stub
		
	}

}
