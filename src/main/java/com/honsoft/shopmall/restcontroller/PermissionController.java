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

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.service.PermissionService;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
	private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
	
	private final PermissionService permissionService;
	public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	@GetMapping
	public ResponseEntity<List<PermissionDto>> getAllPermissions(){
		List<PermissionDto> list = permissionService.getAllPermissions();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PermissionDto> getPermissionById(@PathVariable("id") String permissionId){
		PermissionDto permissionDto = permissionService.getPermissionById(permissionId);
		return ResponseEntity.ok(permissionDto);
	}
	
	@PostMapping
	public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto){
		PermissionDto createdPermissionDto = permissionService.createPermission(permissionDto);
		return ResponseEntity.ok(createdPermissionDto);
	}
	
	@PutMapping
	public ResponseEntity<PermissionDto> updatePermission(@RequestBody PermissionDto permissionDto){
		PermissionDto updatedPermissionDto = permissionService.updatePermission(permissionDto.getPermissionId(),permissionDto);
		return ResponseEntity.ok(updatedPermissionDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deletePermission(@RequestBody PermissionDto permissionDto){
		permissionService.deletePermissionById(permissionDto.getPermissionId());
		return ResponseEntity.ok(permissionDto.getPermissionId()+"  deleted");
	}
	
	@GetMapping("/deleteallbyroleid/{id}")
	public ResponseEntity<Integer> deleteAllPermissionsByRoleId(@PathVariable("id") String roleId){
		int result = permissionService.deleteAllPermissionsByRoleId(roleId);
		return ResponseEntity.ok(result);
	}
}
