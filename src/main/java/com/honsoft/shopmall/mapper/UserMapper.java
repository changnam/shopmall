package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.dto.UserAuthDto;
import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	protected RoleRepository roleRepository;

	@Mapping(target = "roles", expression = "java(toRoleDtoList(user))") // Manually set later
	public abstract UserDto toDto(User user);

	protected List<RoleDto> toRoleDtoList(User user) {
		return user.getUserRoles().stream().map(ur -> {
			Role role = ur.getRole();
			RoleDto dto = new RoleDto();
			dto.setRoleId(role.getRoleId());
			dto.setRoleName(role.getRoleName());
			return dto;
		}).toList();
	}
	
	@Mapping(target = "roles", expression = "java(toRoleDtoList(user))") // Manually set later
	public abstract UserAuthDto toAuthDto(User user);

	@Mapping(target = "userRoles", ignore = true)
	public abstract User toEntity(UserCreateDto userCreateDto);

	@AfterMapping
	public void afterCreateMapping(UserCreateDto dto, @MappingTarget User user) {
		if (dto.getRoleIds() != null) {
			for (String roleId : dto.getRoleIds()) {
				Role role = roleRepository.findById(roleId)
						.orElseThrow(() -> new EntityNotFoundException(roleId + " not found"));
				user.addRole(role);
			}
		}
	}

	@Mapping(target = "userRoles", ignore = true)
	public abstract void updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

	@AfterMapping
	public void afterUpdateMapping(UserUpdateDto dto, @MappingTarget User user) {
		user.clearRoles();
		
		if (dto.getRoleIds() != null) {
			for (String roleId : dto.getRoleIds()) {
				Role role = roleRepository.findById(roleId)
						.orElseThrow(() -> new EntityNotFoundException(roleId + " not found"));
				user.addRole(role);
			}
		}
	}

	public abstract List<UserDto> toDtoList(List<User> users);

	public Page<UserDto> toPage(Page<User> users) {
		return users.map(this::toDto);
	}
}
