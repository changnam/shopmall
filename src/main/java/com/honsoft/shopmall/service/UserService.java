package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto);
	List<UserDto> getAllUsers();
	UserDto getUserById(String userId);
	UserDto updateUser(String userId, UserDto userDto);
	void deleteUserById(String userId);
	void assignRoleToUser(String userId, String roleId);
	void removeRoleFromUser(String userId, String roleId);
	Page<UserDto> getPageUsers(Pageable pageable);
	Optional<UserDto> findById(String id);
}
