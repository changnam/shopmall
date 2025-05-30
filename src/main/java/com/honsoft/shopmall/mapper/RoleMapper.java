package com.honsoft.shopmall.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.repository.PermissionRepository;
import com.honsoft.shopmall.request.RoleCreateRequest;
import com.honsoft.shopmall.request.RoleUpdateDto;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

	@Autowired
	protected PermissionRepository permissionRepository;

	@Autowired
	protected PermissionMapper permissionMapper;
	
	@Mapping(target = "userIds", ignore = true)
	@Mapping(target = "permissions", ignore = true)
	public abstract RoleDto toDto(Role role);
	
	 // AfterMapping hook to populate the list of roles from userRoles
    @AfterMapping
    protected void mapRoles(Role role, @MappingTarget RoleDto dto) {
        if (role.getRolePermissions() != null) {
            List<PermissionDto> permissions = role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .map(this::mapPermissionToDto)
                .collect(Collectors.toList());
            dto.setPermissions(permissions);
        } else {
            dto.setPermissions(List.of());
        }
    }

    // Helper to convert Role entity to RoleDto
    protected PermissionDto mapPermissionToDto(Permission permission) {
    	PermissionDto dto = new PermissionDto();
        dto.setPermissionId(permission.getPermissionId());
        dto.setName(permission.getName());
        dto.setPath(permission.getPath());
        return dto;
    }
    
	@Mapping(target = "rolePermissions", ignore = true)
	@Mapping(target = "roleAssignments", ignore = true)
	public abstract Role toEntity(RoleCreateRequest roleCreateRequest);
	
	@AfterMapping
	public void afterCreateMapping(RoleCreateRequest dto, @MappingTarget Role role) {
		if (dto.getPermissionIds() != null) {
			List<RolePermission> rolePermissions = new ArrayList<>();
			for (String permissionId : dto.getPermissionIds()) {
				Permission permission = permissionRepository.findById(permissionId)
						.orElseThrow(() -> new EntityNotFoundException("Permission not found: " + permissionId));

				RolePermission rp = new RolePermission();
				rp.setId(new RolePermissionId(role.getRoleId(), permissionId));
				rp.setRole(role);
				rp.setPermission(permission);
				rp.setAssignedAt(LocalDateTime.now());

				rolePermissions.add(rp);
			}
			role.setRolePermissions(rolePermissions);
		}
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "rolePermissions", ignore = true)
	@Mapping(target = "roleAssignments", ignore = true)
	public abstract void udpateEntity(RoleUpdateDto roleUpdateDto, @MappingTarget Role role);
	
	@AfterMapping
	public void afterUpdateMapping(RoleUpdateDto dto, @MappingTarget Role role) {
	    // 1. Build new UserRoleId list from dto.roleIds
	    List<RolePermissionId> newPermissionIds = new ArrayList<>();
	    Map<String, Permission> permissionMap = new HashMap<>();

	    if (dto.getPermissionIds() != null) {
	        for (String permissionId : dto.getPermissionIds()) {
	            Permission permission = permissionRepository.findById(permissionId)
	                .orElseThrow(() -> new EntityNotFoundException(permissionId + " not found"));
	            newPermissionIds.add(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
	            permissionMap.put(permission.getPermissionId(), permission);
	        }
	    }

	    // 2. Remove roles that are no longer in newRoleIds
	    List<RolePermission> permissionsToRemove = new ArrayList<>();
	    for (RolePermission rolePermission : role.getRolePermissions()) {
	        if (!newPermissionIds.contains(rolePermission.getId())) {
	            permissionsToRemove.add(rolePermission);
	        }
	    }
	    
	    for (RolePermission rolePermission : permissionsToRemove) {
	    	rolePermission.getPermission().getRolePermissions().remove(rolePermission);
	    	rolePermission.setRole(null);
	    	rolePermission.setPermission(null);
//	        role.removeRolePermission(rolePermission);
	    }

	    // 3. Add new roles that do not exist yet
	    for (RolePermissionId rolePermissionId : newPermissionIds) {
	        boolean exists = role.getRolePermissions().stream()
	            .anyMatch(rp -> rp.getId().equals(rolePermissionId));

	        if (!exists) {
	            Permission permission = permissionMap.get(rolePermissionId.getPermissionId()); // Already loaded above
	            RolePermission newRolePermission = new RolePermission();
	            newRolePermission.setId(rolePermissionId);
	            newRolePermission.setRole(role);
	            newRolePermission.setPermission(permission);
//	            role.addRolePermission(newRolePermission);
	            permission.getRolePermissions().add(newRolePermission); // maintain bidirectional link
	        }
	    }
	}
	
	
	public abstract List<RoleDto> toDtoList(List<Role> roles);
	
	public Page<RoleDto> toPage(Page<Role> roles){
		return roles.map(this::toDto);
	}

}
