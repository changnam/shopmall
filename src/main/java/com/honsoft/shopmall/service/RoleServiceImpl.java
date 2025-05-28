package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.mapper.PermissionMapper;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.PermissionRepository;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;
import com.honsoft.shopmall.request.RoleCreateDto;
import com.honsoft.shopmall.request.RoleUpdateDto;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

	private final BizExceptionMessageService bizExceptionMessageService;
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, UserRepository userRepository,
			UserMapper userMapper, PermissionRepository permissionRepository, PermissionMapper permissionMapper, BizExceptionMessageService bizExceptionMessageService) {
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
		this.bizExceptionMessageService = bizExceptionMessageService;
	}

	@Transactional
	@Override
	public RoleDto createRole(RoleCreateDto roleCreateDto) {
		roleRepository.findById(roleCreateDto.getRoleId()).ifPresent(r -> {throw bizExceptionMessageService.createLocalizedException("ROLE_ALREADY_EXIST");});
		Role role = roleMapper.toEntity(roleCreateDto);
		Role savedRole = roleRepository.save(role);
		return roleMapper.toDto(savedRole);
	}

	@Override
	public Page<RoleDto> getAllRoles(Pageable pageable) {
		Page<Role> roles = roleRepository.findAll(pageable);
		Page<RoleDto> dtos = roleMapper.toPage(roles);
		return dtos;
	}

	@Override
	public RoleDto getRoleById(String id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
		return roleMapper.toDto(role);
	}

	@Transactional
	@Override
	public RoleDto updateRole(String id, RoleUpdateDto roleUpdateDto) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Role not found: " + id));

		roleMapper.udpateEntity(roleUpdateDto, existingRole);

//		Role updatedRole = roleRepository.save(existingRole);
		return roleMapper.toDto(existingRole);
	}

	@Transactional
	@Override
	public void deleteRoleById(String id) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Role not found: " + id));
		roleRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void assignUserToRole(String roleId, String userId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
		userRole.setAssignedAt(LocalDateTime.now());

		role.getUserRoles().add(userRole);
		roleRepository.save(role); // Handles join table automatically
	}

	@Transactional
	@Override
	public void removeUserFromRole(String roleId, String userId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		role.getUserRoles().removeIf(userRole -> userRole.getUser().getUserId().equals(userId));
		roleRepository.save(role);
	}

	@Transactional
	@Override
	public void assignPermissionToRole(String roleId, String permissionId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		Permission permission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new RuntimeException("PermissionId not found"));

		RolePermission rolePermission = new RolePermission();
		rolePermission.setRole(role);
		rolePermission.setPermission(permission);
		rolePermission.setId(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
		rolePermission.setAssignedAt(LocalDateTime.now());

		role.getRolePermissions().add(rolePermission);
		roleRepository.save(role); // Handles join table automatically

	}

	@Transactional
	@Override
	public void removePermissionFromRole(String roleId, String permissionId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		role.getRolePermissions()
				.removeIf(rolePermission -> rolePermission.getPermission().getPermissionId().equals(permissionId));
		roleRepository.save(role);

	}

	@Override
	public List<RoleDto> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		List<RoleDto> dtos = roleMapper.toDtoList(roles);
		return dtos;
	}
}
