package com.honsoft.shopmall.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.RolePermission;
import com.honsoft.shopmall.entity.RolePermissionId;
import com.honsoft.shopmall.repository.PermissionRepository;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    @Autowired
    protected PermissionRepository permissionRepository;

    public abstract RoleDto toDto(Role role);

    public abstract Role toEntity(RoleDto roleDto);
    
    public abstract List<RoleDto> toDtoList(List<Role> roles);

    public abstract List<Role> toEntityList(List<RoleDto> roleDto);

    @AfterMapping
    protected void mapPermissions(RoleDto dto, @MappingTarget Role role) {
        if (dto.getPermissionIds() == null) return;

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
    }
    
    @AfterMapping
    protected void extractPermissionIds(Role role, @MappingTarget RoleDto dto) {
        if (role.getRolePermissions() == null) return;

        List<String> permissionIds = role.getRolePermissions().stream()
            .map(rp -> rp.getPermission().getPermissionId())
            .collect(Collectors.toList());

        dto.setPermissionIds(permissionIds);
    }
}
