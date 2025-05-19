package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.entity.Account;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
	@Mapping(target = "roles", ignore = true)
	public abstract AccountDto toDto(Account account);
	@Mapping(target = "roles", ignore = true)
	public abstract Account toEntity(AccountDto accountDto);
	
	public abstract List<AccountDto> toDtoList(List<Account> accounts);
	public abstract List<Account> toEntityList(List<AccountDto> accountDtos);
	
	

}
