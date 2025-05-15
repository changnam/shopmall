package com.honsoft.shopmall.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
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

	@Override
	public void assignRoleToUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Role role = roleRepository.findById(roleId)
	        .orElseThrow(() -> new RuntimeException("Role not found"));

	    user.getRoles().add(role);
	    userRepository.save(user);  // Handles join table automatically
	}
	
	@Override
	public void removeRoleFromUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    user.getRoles().removeIf(role -> role.getRoleId().equals(roleId));
	    userRepository.save(user);
	}


}
