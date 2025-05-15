package com.honsoft.shopmall.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.repository.PermissionRepository;
import com.honsoft.shopmall.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected PermissionRepository permissionRepository;

	@Mapping(target = "userIds", ignore = true)
	@Mapping(target = "permissionIds", ignore = true)
	public abstract RoleDto toDto(Role role);

	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "rolePermissions", ignore = true)
	public abstract Role toEntity(RoleDto roleDto);

	public abstract List<RoleDto> toDtoList(List<Role> roles);

	public abstract List<Role> toEntityList(List<RoleDto> roleDto);

	@AfterMapping
	protected void mapPermissions(RoleDto dto, @MappingTarget Role role) {
		if (dto.getPermissionIds() == null && dto.getUserIds() == null)
			return;
		else if (dto.getPermissionIds() != null) {
			List<RolePermission> rolePermissions = new ArrayList<>();
			for (String permissionId : dto.getPermissionIds()) {
				Permission permission = permissionRepository.findById(permissionId)
						.orElseThrow(() -> new RuntimeException("Permission not found: " + permissionId));

				RolePermission rolePermission = new RolePermission();
				rolePermission.setRole(role);
				rolePermission.setPermission(permission);
				rolePermission.setId(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
				rolePermission.setAssignedAt(LocalDateTime.now());

				rolePermissions.add(rolePermission);
			}
			role.setRolePermissions(rolePermissions);
		} else if (dto.getUserIds() != null) {
			List<UserRole> userRoles = new ArrayList<>();
			for (String userId : dto.getUserIds()) {
				User user = userRepository.findById(userId)
						.orElseThrow(() -> new RuntimeException("User not found: " + userId));

				UserRole userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUser(user);
				userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
				userRole.setAssignedAt(LocalDateTime.now());

				userRoles.add(userRole);
			}
			role.setUserRoles(userRoles);
		}
	}

	@AfterMapping
	protected void extractPermissionIds(Role role, @MappingTarget RoleDto dto) {
		if (role.getRolePermissions() == null && role.getUserRoles() == null)
			return;
		else if (role.getRolePermissions() != null) {
			List<String> permissionIds = role.getRolePermissions().stream()
					.map(rp -> rp.getPermission().getPermissionId()).collect(Collectors.toList());

			dto.setPermissionIds(permissionIds);
		} else if (role.getUserRoles() != null) {
			List<String> userIds = role.getUserRoles().stream().map(rp -> rp.getUser().getUserId())
					.collect(Collectors.toList());

			dto.setUserIds(userIds);
		}
	}
}
