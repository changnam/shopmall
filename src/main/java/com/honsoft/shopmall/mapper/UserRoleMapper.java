package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.UserRoleDto;
import com.honsoft.shopmall.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
	
	UserRoleDto toDto(UserRole userRole);
	UserRole toEntity(UserRoleDto userRoleDto);
	
	List<UserRoleDto> toDtoList(List<UserRole> userRoles);
	List<UserRole> toEntityList(List<UserRoleDto> userRoleDtos);

}
