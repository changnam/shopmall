package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	private final BizExceptionMessageService bizExceptionMessageService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	public UserServiceImpl(BizExceptionMessageService bizExceptionMessageService,UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper) {
		this.bizExceptionMessageService = bizExceptionMessageService;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
	}

	@Transactional
//	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserDto createUser(UserCreateDto userCreateDto) {
		User user = userMapper.toEntity(userCreateDto);
		//check email already exists
		userRepository.findByEmail(user.getEmail()).ifPresent(a -> {throw bizExceptionMessageService.createLocalizedException("EMAIL_ALREADY_EXIST");});

				
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
		User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		UserDto userDto = userMapper.toDto(user);
		return userDto;
	}

	@Transactional
	@Override
	public UserDto updateUser(String userId, UserUpdateDto userUpdateDto) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		userMapper.updateEntity(userUpdateDto, existingUser);
		
		User updatedUser = userRepository.save(existingUser);
		return userMapper.toDto(updatedUser);
	}

	@Transactional
	@Override
	public void deleteUserById(String userId) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		userRepository.deleteById(userId);		
	}

	@Transactional
	@Override
	public void assignRoleToUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Role role = roleRepository.findById(roleId)
	        .orElseThrow(() -> new RuntimeException("Role not found"));

	    UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
        userRole.setAssignedAt(LocalDateTime.now());
        
	    user.getUserRoles().add(userRole);
	    userRepository.save(user);  // Handles join table automatically
	}
	
	@Transactional
	@Override
	public void removeRoleFromUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    user.getUserRoles().removeIf(userRole -> userRole.getRole().getRoleId().equals(roleId));
	    userRepository.save(user);
	}

	@Override
	public Page<UserDto> getPageUsers(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		Page<UserDto> dtos = userMapper.toPage(users);
		return dtos;
	}

	@Override
	public Optional<UserDto> findById(String id) {
		 return userRepository.findById(id).map(userMapper::toDto);
	}


}
