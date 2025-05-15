package com.honsoft.shopmall.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
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
        if (dto.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());
            role.setPermissions(permissions);
        }
    }

    @AfterMapping
    protected void mapPermissionIds(@MappingTarget RoleDto dto, Role role) {
        if (role.getPermissions() != null) {
            List<String> ids = role.getPermissions().stream()
                .map(Permission::getPermissionId)
                .collect(Collectors.toList());
            dto.setPermissionIds(ids);
        }
    }
}
