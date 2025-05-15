package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	PermissionDto toDto(Permission permission);
	Permission toEntity(PermissionDto permissionDto);
	
	List<PermissionDto> toDtoList(List<Permission> permissions);
	List<Permission> toEntityList(List<PermissionDto> permissionDtos);
}
