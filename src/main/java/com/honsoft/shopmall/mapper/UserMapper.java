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
import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "roleIds", ignore = true) // Manually set later
    public abstract UserDto toDto(User user);

    @Mapping(target = "userRoles", ignore = true)
    public abstract User toEntity(UserDto userDto);
    
    public abstract List<UserDto> toDtoList(List<User> users);
    
    public abstract List<User> toEntityList(List<UserDto> userDtos);
	
    @AfterMapping
    protected void mapRoles(UserDto dto, @MappingTarget User user) {
        if (dto.getRoleIds() == null) return;

        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : dto.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
            userRole.setAssignedAt(LocalDateTime.now());

            userRoles.add(userRole);
        }
        user.setUserRoles(userRoles);
    }
    
    @AfterMapping
    protected void mapRoleIds( User user,@MappingTarget UserDto dto) {        
        if (user.getUserRoles() == null) return;

        List<String> roleIds = user.getUserRoles().stream()
            .map(rp -> rp.getRole().getRoleId())
            .collect(Collectors.toList());

        dto.setRoleIds(roleIds);
    }

}
