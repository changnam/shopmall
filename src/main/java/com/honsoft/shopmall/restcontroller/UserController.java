package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.honsoft.shopmall.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> list = userService.getAllUsers();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<UserDto>> getPageUsers( @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
		Page<UserDto> list = userService.getPageUsers(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") String UserId){
		UserDto UserDto = userService.getUserById(UserId);
		return ResponseEntity.ok(UserDto);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto UserDto){
		UserDto createdUserDto = userService.createUser(UserDto);
		return ResponseEntity.ok(createdUserDto);
	}
	
	@PutMapping
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto UserDto){
		UserDto updatedUserDto = userService.updateUser(UserDto.getUserId(),UserDto);
		return ResponseEntity.ok(updatedUserDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestBody UserDto UserDto){
		userService.deleteUserById(UserDto.getUserId());
		return ResponseEntity.ok(UserDto.getUserId()+"  deleted");
	}
}
