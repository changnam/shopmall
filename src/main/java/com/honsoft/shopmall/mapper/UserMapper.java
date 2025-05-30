package com.honsoft.shopmall.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.honsoft.shopmall.entity.UserRoleAssignment;
//import com.honsoft.shopmall.entity.UserRole;
//import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRoleAssignmentRepository;
import com.honsoft.shopmall.request.UserCreateRequest;
import com.honsoft.shopmall.request.UserUpdateRequest;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected UserRoleAssignmentRepository userRoleAssignmentRepository;

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
	public abstract User toEntity(UserCreateRequest userCreateRequest);
//
//	  @AfterMapping
//      public void afterCreateMapping(UserCreateRequest request, @MappingTarget User user) {
//              if (request.getRoleIds() != null) {
//                      List<UserRoleAssignment> uras = new ArrayList<>();
//                      for (String roleId : request.getRoleIds()) {
//                              Role role = roleRepository.findById(roleId)
//                                              .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
//
//                              UserRoleAssignment ura = new UserRoleAssignment();
////                              ura.setId();
//                              ura.setUser(user);
//                              ura.setRole(role);
//                              ura.setAssignedAt(LocalDateTime.now());
//                              userRoleAssignmentRepository.save(ura);
////                              uras.add(ura);
//                              
//                      }
////                      user.setUserRoles(userRoles);
////                      userRoleAssignmentRepository.saveAll(uras);
//              }
//      }

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
	@Mapping(target = "email", ignore = true)
	public abstract void updateEntity(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    // AfterMapping hook to populate the list of roles from userRoles
   @AfterMapping
   protected void mapRoles(User user, @MappingTarget UserDto dto) {
       if (user.getRoleAssignments() != null) {
           List<RoleDto> roles = user.getRoleAssignments().stream()
               .map(UserRoleAssignment::getRole)
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
