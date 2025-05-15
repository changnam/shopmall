package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.mapper.PermissionMapper;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.repository.PermissionRepository;
import com.honsoft.shopmall.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {
	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;

	public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper,
			RoleRepository roleRepository, RoleMapper roleMapper) {
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
	}

	@Transactional
	@Override
	public PermissionDto createPermission(PermissionDto permissionDto) {
		Permission permission = permissionMapper.toEntity(permissionDto);
		Permission saved = permissionRepository.save(permission);
		PermissionDto savedDto = permissionMapper.toDto(saved);
		return savedDto;
	}

	@Override
	public PermissionDto getPermissionById(String permissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PermissionDto> getAllPermissions() {
		List<Permission> permissions = permissionRepository.findAll();
		List<PermissionDto> dtos = permissionMapper.toDtoList(permissions);

		return dtos;
	}

	@Transactional
	@Override
	public PermissionDto updatePermission(String permissionId, PermissionDto permissionDto) {
		Permission existingPermission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new EntityNotFoundException(permissionId));
		// MapStruct @AfterMapping will handle permissionIds
		Permission updatingPermission = permissionMapper.toEntity(permissionDto); // update 할 role 에 permissions 정보가 모드
																					// 로드되어 있다.

		existingPermission.getRolePermissions().clear(); // permission 클리어 여부를 판단할것 . 기존 permission 을 삭제할것인지

		existingPermission.setName(updatingPermission.getName());
		existingPermission.setRolePermissions(updatingPermission.getRolePermissions()); // 새로운 permissions 셋팅
		existingPermission.setPath(updatingPermission.getPath());
		existingPermission.setHttpMethod(updatingPermission.getHttpMethod());

		Permission updatedPermission = permissionRepository.save(existingPermission);
		return permissionMapper.toDto(updatedPermission);
	}

	@Transactional
	@Override
	public void deletePermissionById(String permissionId) {
		Permission existingPermission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new EntityNotFoundException(permissionId));
		permissionRepository.deleteById(permissionId);

	}

	@Transactional
	@Override
	public void assignRoleToPermission(String permissionId, String roleId) {
		Permission permission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new RuntimeException("Permission not found"));

		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		RolePermission rolePermission = new RolePermission();
		rolePermission.setRole(role);
		rolePermission.setPermission(permission);
		rolePermission.setId(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
		rolePermission.setAssignedAt(LocalDateTime.now());

		permission.getRolePermissions().add(rolePermission);
		permissionRepository.save(permission); // Handles join table automatically

	}

	@Transactional
	@Override
	public void removeRoleFromPermission(String permissionId, String roleId) {
		Permission permission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new RuntimeException("Permission not found"));

		permission.getRolePermissions().removeIf(rolePermission -> rolePermission.getRole().getRoleId().equals(roleId));
		permissionRepository.save(permission);
	}

}
