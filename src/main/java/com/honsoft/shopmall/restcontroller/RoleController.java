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

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.request.RoleCreateDto;
import com.honsoft.shopmall.request.RoleUpdateDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseEntity<Object> getAllroles(
			@PageableDefault(page = 0, size = 5, sort = "roleName", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<RoleDto> list = roleService.getAllRoles(pageable);
		return ResponseHandler.responseBuilder("role pages success", HttpStatus.OK, list);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllrolesReal() {
		List<RoleDto> list = roleService.getAllRoles();
		return ResponseHandler.responseBuilder("role pages success", HttpStatus.OK, list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getroleById(@PathVariable("id") String roleId) {
		RoleDto roleDto = roleService.getRoleById(roleId);
		return ResponseHandler.responseBuilder("role get success", HttpStatus.OK, roleDto);
	}

	@PostMapping
	public ResponseEntity<Object> createrole(@RequestBody RoleCreateDto roleCreateDto) {
		RoleDto createdroleDto = roleService.createRole(roleCreateDto);
		return ResponseHandler.responseBuilder("role create success", HttpStatus.OK, createdroleDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updaterole(@PathVariable("id") String roleId,@RequestBody RoleUpdateDto roleUpdateDto) {
		RoleDto updatedroleDto = roleService.updateRole(roleId, roleUpdateDto);
		return ResponseHandler.responseBuilder("role update success", HttpStatus.OK, updatedroleDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleterole(@PathVariable("id") String roleId) {
		roleService.deleteRoleById(roleId);
		return ResponseHandler.responseBuilder("role delete success", HttpStatus.OK, roleId + "  deleted");
	}
}
