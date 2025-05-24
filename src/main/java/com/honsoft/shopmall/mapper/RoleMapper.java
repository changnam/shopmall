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

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface RoleMapper {

	@Mapping(target = "userIds", ignore = true)
	@Mapping(target = "permissionIds", ignore = true)
	RoleDto toDto(Role role);

	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "rolePermissions", ignore = true)
	Role toEntity(RoleDto roleDto);

	List<RoleDto> toDtoList(List<Role> roles);

	List<Role> toEntityList(List<RoleDto> roleDto);

}
