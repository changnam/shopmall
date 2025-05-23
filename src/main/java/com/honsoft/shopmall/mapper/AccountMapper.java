package com.honsoft.shopmall.mapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.entity.Account;
import com.honsoft.shopmall.entity.AccountRole;
import com.honsoft.shopmall.entity.ERole;
import com.honsoft.shopmall.repository.AccountRoleRepository;

@Mapper(componentModel = "spring", uses = { AccountMapper.class })
public interface AccountMapper {

	@Mapping(target = "roles", source = "roles", qualifiedByName = "mapStringsToRoles")
	Account toEntity(AccountDto dto, @Context AccountRoleRepository roleRepo);

	@Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStrings")
	@Mapping(target = "age", ignore = true)
	@Mapping(target = "koreanAge", ignore = true)
	AccountDto toDto(Account account);

	List<AccountDto> toDtoList(List<Account> accounts);

	List<Account> toEntityList(List<AccountDto> accountDtos, @Context AccountRoleRepository roleRepo);

	default Page<AccountDto> toDtoPage(Page<Account> page) {
		return page.map(this::toDto);
	}

	@Named("mapRolesToStrings")
	default List<String> mapRolesToStrings(Set<AccountRole> roles) {
		return roles.stream().map(role -> role.getErole().name()).collect(Collectors.toList());
	}

	@Named("mapStringsToRoles")
	default Set<AccountRole> mapStringsToRoles(List<String> roleNames, @Context AccountRoleRepository roleRepo) {
		return roleNames.stream().map(name -> {
			ERole erole = ERole.valueOf(name);
			return roleRepo.findByErole(erole)
					.orElseThrow(() -> new IllegalArgumentException("Role not found: " + name));
		}).collect(Collectors.toSet());
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", expression = "java(mapRoles(dto.getRoles(), roleRepo))")
	void updateAccountFromDto(AccountDto dto, @MappingTarget Account entity, @Context AccountRoleRepository roleRepo);

	default Set<AccountRole> mapRoles(List<String> roleNames, @Context AccountRoleRepository roleRepo) {
	    if (roleNames == null) return null;

	    return roleNames.stream()
	        .map(roleName -> {
	            try {
	                ERole erole = ERole.valueOf(roleName); // convert String to Enum
	                return roleRepo.findByErole(erole)
	                               .orElseThrow(() -> new IllegalArgumentException("Role not found: " + erole));
	            } catch (IllegalArgumentException e) {
	                throw new RuntimeException("Invalid role name: " + roleName, e);
	            }
	        })
	        .collect(Collectors.toSet());
	}
}
