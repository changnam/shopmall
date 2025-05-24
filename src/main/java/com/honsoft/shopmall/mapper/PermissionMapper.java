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

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface PermissionMapper {
	
	// Entity to DTO
    @Mapping(target = "roleIds", ignore = true) // Manually set later
	PermissionDto toDto(Permission permission);
	
	@Mapping(target = "rolePermissions", ignore = true)
	Permission toEntity(PermissionDto permissionDto);
	
	List<PermissionDto> toDtoList(List<Permission> permissions);
	List<Permission> toEntityList(List<PermissionDto> permissionDtos);
	
}
