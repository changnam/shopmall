package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.AccountRoleDto;
import com.honsoft.shopmall.entity.AccountRole;

@Mapper(componentModel = "spring")
public interface AccountRoleMapper {
	AccountRoleDto toDto(AccountRole accountRole);
	AccountRole toEntity(AccountRoleDto accountRoleDto);
	
	List<AccountRoleDto> toDtoList(List<AccountRole> accountRoles);
	List<AccountRole> toEntityList(List<AccountRoleDto> accountRoleDtos);
	
}
