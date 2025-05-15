package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	private final RoleService roleService;
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping
	public ResponseEntity<List<RoleDto>> getAllroles(){
		List<RoleDto> list = roleService.getAllRoles();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleDto> getroleById(@PathVariable("id") String roleId){
		RoleDto roleDto = roleService.getRoleById(roleId);
		return ResponseEntity.ok(roleDto);
	}
	
	@PostMapping
	public ResponseEntity<RoleDto> createrole(@RequestBody RoleDto roleDto){
		RoleDto createdroleDto = roleService.createRole(roleDto);
		return ResponseEntity.ok(createdroleDto);
	}
	
	@PutMapping
	public ResponseEntity<RoleDto> updaterole(@RequestBody RoleDto roleDto){
		RoleDto updatedroleDto = roleService.updateRole(roleDto.getRoleId(),roleDto);
		return ResponseEntity.ok(updatedroleDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleterole(@RequestBody RoleDto roleDto){
		roleService.deleteRoleById(roleDto.getRoleId());
		return ResponseEntity.ok(roleDto.getRoleId()+"  deleted");
	}
}
