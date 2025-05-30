package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.request.UserRoleAssignmentRequest;
import com.honsoft.shopmall.request.UserCreateRequest;
import com.honsoft.shopmall.request.UserPasswordUpdateRequest;
import com.honsoft.shopmall.request.UserUpdateRequest;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllUsers(){
		List<UserDto> list = userService.getAllUsers();
		return ResponseHandler.responseBuilder("get success", HttpStatus.OK, list);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<UserDto>> getPageUsers( @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
		Page<UserDto> list = userService.getPageUsers(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") String UserId){
		UserDto UserDto = userService.getUserById(UserId);
		return ResponseHandler.responseBuilder("get success", HttpStatus.OK, UserDto);
	}
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest userCreateRequest){
		UserDto createdUserDto = userService.createUser(userCreateRequest);
//		String userId = userService.createUser(userCreateRequest);
		return ResponseHandler.responseBuilder("create success", HttpStatus.OK, createdUserDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable("id") String userId,@RequestBody UserUpdateRequest userUpdateRequest){
		UserDto updatedUserDto = userService.updateUser(userId,userUpdateRequest);
		return ResponseHandler.responseBuilder("update success", HttpStatus.OK, updatedUserDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") String userId){
		userService.deleteUserById(userId);
		return ResponseHandler.responseBuilder("delete success", HttpStatus.OK, userId +" delete success");
	}
	
	@PostMapping("/assign-roles")
	public ResponseEntity<?> assignRoles(@RequestBody UserRoleAssignmentRequest userRoleAssignmentRequest) {
	    userService.assignRoles(userRoleAssignmentRequest);
	    return ResponseHandler.responseBuilder("role assignment success", HttpStatus.OK, userRoleAssignmentRequest.getRoleIds());
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
	    UserDto updated = userService.changePassword(userPasswordUpdateRequest);
	    return ResponseHandler.responseBuilder("password change success", HttpStatus.OK, updated);
	}

}
