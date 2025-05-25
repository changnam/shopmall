package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.request.RoleCreateDto;
import com.honsoft.shopmall.request.RoleUpdateDto;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

	public abstract RoleDto toDto(Role role);
	
	@Mapping(target = "rolePermissions", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	public abstract Role toEntity(RoleCreateDto roleCreateDto);
	
	@Mapping(target = "rolePermissions", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	public abstract void udpateEntity(RoleUpdateDto roleUpdateDto, @MappingTarget Role role);
	
	public abstract List<RoleDto> toDtoList(List<Role> roles);
	
	public Page<RoleDto> toPage(Page<Role> roles){
		return roles.map(this::toDto);
	}

}
