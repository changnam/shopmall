package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.request.RoleAssignmentRequestDto;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;

public interface UserService {
	UserDto createUser(UserCreateDto userCreateDto);
	List<UserDto> getAllUsers();
	UserDto getUserById(String userId);
	UserDto updateUser(String userId, UserUpdateDto userUpdateDto);
	void deleteUserById(String userId);
	void assignRoleToUser(String userId, String roleId);
	void removeRoleFromUser(String userId, String roleId);
	Page<UserDto> getPageUsers(Pageable pageable);
	Optional<UserDto> findById(String id);
	void assignRoleToUser(String roleId, String userId, String assignedBy);
	void assignRoles(RoleAssignmentRequestDto roleAssignmentRequestDto);
	
}
