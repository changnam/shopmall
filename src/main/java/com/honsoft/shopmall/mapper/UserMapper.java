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
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.repository.RoleRepository;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserMapper {

	@Mapping(target = "roleIds", ignore = true) // Manually set later
    UserDto toDto(User user);

    @Mapping(target = "userRoles", ignore = true)
    User toEntity(UserDto userDto);
    
    List<UserDto> toDtoList(List<User> users);
    
    List<User> toEntityList(List<UserDto> userDtos);
    
    default Page<UserDto> toPage(Page<User> users) {
        return users.map(this::toDto);
    }
	
}
