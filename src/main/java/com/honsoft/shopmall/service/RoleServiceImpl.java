package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, UserRepository userRepository,
			UserMapper userMapper, PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
	}

	@Transactional
	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = roleMapper.toEntity(roleDto);
		Role savedRole = roleRepository.save(role);
		return roleMapper.toDto(savedRole);
	}

	@Override
	public List<RoleDto> getAllRoles() {
		return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public RoleDto getRoleById(String id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
		return roleMapper.toDto(role);
	}

	@Transactional
	@Override
	public RoleDto updateRole(String id, RoleDto roleDto) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Role not found: " + id));

		// MapStruct @AfterMapping will handle permissionIds
		Role forUpdateRole = roleMapper.toEntity(roleDto); // update 할 role 에 permissions 정보가 모드 로드되어 있다.

		existingRole.getRolePermissions().clear(); // permission 클리어 여부를 판단할것 . 기존 permission 을 삭제할것인지

		existingRole.setRoleName(roleDto.getRoleName());
		existingRole.setRolePermissions(forUpdateRole.getRolePermissions()); // 새로운 permissions 셋팅

		Role updatedRole = roleRepository.save(existingRole);
		return roleMapper.toDto(updatedRole);
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
}
