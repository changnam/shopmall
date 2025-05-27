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
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
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

	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	public abstract User toEntity(UserCreateDto userCreateDto);

	@AfterMapping
	public void afterCreateMapping(UserCreateDto dto, @MappingTarget User user) {
		if (dto.getRoleIds() != null) {
			List<UserRole> userRoles = new ArrayList<>();
			for (String roleId : dto.getRoleIds()) {
				Role role = roleRepository.findById(roleId)
						.orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

				UserRole ur = new UserRole();
				ur.setId(new UserRoleId(user.getUserId(), roleId));
				ur.setUser(user);
				ur.setRole(role);
				ur.setAssignedAt(LocalDateTime.now());

				userRoles.add(ur);
			}
			user.setUserRoles(userRoles);
		}
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "userId", ignore = true)
	public abstract void updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

	
	@AfterMapping
	public void afterUpdateMapping(UserUpdateDto dto, @MappingTarget User user) {
	    // 1. Build new UserRoleId list from dto.roleIds
	    List<UserRoleId> newRoleIds = new ArrayList<>();
	    Map<String, Role> roleMap = new HashMap<>();

	    if (dto.getRoleIds() != null) {
	        for (String roleId : dto.getRoleIds()) {
	            Role role = roleRepository.findById(roleId)
	                .orElseThrow(() -> new EntityNotFoundException(roleId + " not found"));
	            newRoleIds.add(new UserRoleId(user.getUserId(), role.getRoleId()));
	            roleMap.put(role.getRoleId(), role);
	        }
	    }

	    // 2. Remove roles that are no longer in newRoleIds
	    List<UserRole> rolesToRemove = new ArrayList<>();
	    for (UserRole userRole : user.getUserRoles()) {
	        if (!newRoleIds.contains(userRole.getId())) {
	            rolesToRemove.add(userRole);
	        }
	    }
	    for (UserRole userRole : rolesToRemove) {
	        userRole.getRole().getUserRoles().remove(userRole);
	        userRole.setUser(null);
	        userRole.setRole(null);
	        user.removeUserRole(userRole);
	    }

	    // 3. Add new roles that do not exist yet
	    for (UserRoleId userRoleId : newRoleIds) {
	        boolean exists = user.getUserRoles().stream()
	            .anyMatch(ur -> ur.getId().equals(userRoleId));

	        if (!exists) {
	            Role role = roleMap.get(userRoleId.getRoleId()); // Already loaded above
	            UserRole newUserRole = new UserRole();
	            newUserRole.setId(userRoleId);
	            newUserRole.setUser(user);
	            newUserRole.setRole(role);
	            user.addUserRole(newUserRole);
	            role.getUserRoles().add(newUserRole); // maintain bidirectional link
	        }
	    }
	}
	
	 // AfterMapping hook to populate the list of roles from userRoles
    @AfterMapping
    protected void mapRoles(User user, @MappingTarget UserDto dto) {
        if (user.getUserRoles() != null) {
            List<RoleDto> roles = user.getUserRoles().stream()
                .map(UserRole::getRole)
                .map(this::mapRoleToDto)
                .collect(Collectors.toList());
            dto.setRoles(roles);
        } else {
            dto.setRoles(List.of());
        }
    }

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
