package com.honsoft.shopmall.service;

import java.util.List;

import com.honsoft.shopmall.dto.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto);
	List<UserDto> getAllUsers();
	UserDto getUserById(String userId);
	UserDto updateUser(String userId, UserDto userDto);
	void deleteUserById(String userId);
	void assignRoleToUser(String userId, String roleId);
	void removeRoleFromUser(String userId, String roleId);
}
