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

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class PermissionMapper {
	
	@Autowired
    protected RoleRepository roleRepository;
	
	// Entity to DTO
    @Mapping(target = "roleIds", ignore = true) // Manually set later
	public abstract PermissionDto toDto(Permission permission);
	
	@Mapping(target = "rolePermissions", ignore = true)
	public abstract Permission toEntity(PermissionDto permissionDto);
	
	public abstract List<PermissionDto> toDtoList(List<Permission> permissions);
//	public abstract List<Permission> toEntityList(List<PermissionDto> permissionDtos);
	
	  @AfterMapping
	    protected void mapRoles(PermissionDto dto, @MappingTarget Permission permission) {
	        if (dto.getRoleIds() == null) return;

	        List<RolePermission> rolePermissions = new ArrayList<>();
	        for (String roleId : dto.getRoleIds()) {
	            Role role = roleRepository.findById(roleId)
	                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

	            RolePermission rolePermission = new RolePermission();
	            rolePermission.setRole(role);
	            rolePermission.setPermission(permission);
	            rolePermission.setId(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
	            rolePermission.setAssignedAt(LocalDateTime.now());

	            rolePermissions.add(rolePermission);
	        }
	        permission.setRolePermissions(rolePermissions);
	    }
	    
	    @AfterMapping
	    protected void extractPermissionIds(Permission permission, @MappingTarget PermissionDto dto) {
	        if (permission.getRolePermissions() == null) return;

	        List<String> roleIds = permission.getRolePermissions().stream()
	            .map(rp -> rp.getRole().getRoleId())
	            .collect(Collectors.toList());

	        dto.setRoleIds(roleIds);
	    }
}
