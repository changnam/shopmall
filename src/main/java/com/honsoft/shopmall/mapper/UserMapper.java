package com.honsoft.shopmall.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleRepository roleRepository;

    public abstract UserDto toDto(User user);

    public abstract User toEntity(UserDto userDto);
    
    public abstract List<UserDto> toDtoList(List<User> users);
    
    public abstract List<User> toEntityList(List<UserDto> userDtos);

    @AfterMapping
    protected void mapRoles(UserDto userDto, @MappingTarget User user) {
        if (userDto.getRoleIds() != null) {
            List<Role> roles = roleRepository.findAllById(userDto.getRoleIds());
            user.setRoles(roles);
        }
    }
    
    @AfterMapping
    protected void mapRoleIds( User user,@MappingTarget UserDto userDto) {
        if (user.getRoles() != null) {
            List<String> roleIds = user.getRoles().stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
            userDto.setRoleIds(roleIds);
        }
    }

}
