package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Role;

@Mapper(componentModel = "spring",uses = UserRoleMapper.class)
public interface RoleMapper {
	RoleDto toDto(Role role);
	Role toEntity(RoleDto roleDto);
	
	List<RoleDto> toDtoList(List<Role> roles);
	List<Role> toEntityList(List<RoleDto> roleDtos);
}
