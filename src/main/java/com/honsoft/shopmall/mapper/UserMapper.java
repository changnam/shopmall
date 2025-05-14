package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.User;

@Mapper(componentModel = "spring",uses = UserRoleMapper.class)
public interface UserMapper {
	UserDto toDto(User user);
	User toEntity(UserDto userDto);
	
	List<UserDto> toDtoList(List<User> users);
	List<User> toEntityList(List<UserDto> userDtos);

}
