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

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
//import com.honsoft.shopmall.entity.UserRole;
//import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected RoleMapper roleMapper;

	@Mapping(target = "roles", ignore = true) // Manually set later
	public abstract UserDto toDto(User user);

	@Mapping(target = "roleAssignments", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	public abstract User toEntity(UserCreateDto userCreateDto);


	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "roleAssignments", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "userId", ignore = true)
	public abstract void updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);


    // Helper to convert Role entity to RoleDto
    protected RoleDto mapRoleToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }
    
	public abstract List<UserDto> toDtoList(List<User> users);

	public Page<UserDto> toPage(Page<User> users) {
		return users.map(this::toDto);
	}
}
