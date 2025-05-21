package com.honsoft.shopmall.service;

import java.util.List;

import com.honsoft.shopmall.dto.AccountRoleDto;

public interface AccountRoleService {
	List<AccountRoleDto> getAllAccountRoles();
	AccountRoleDto createAccountRole(AccountRoleDto accountRoleDto);
	AccountRoleDto getAccountRoleById(Long id);
	AccountRoleDto updateAccountRole(Long id, AccountRoleDto accountRoleDto);
	void deleteAccountRoleById(Long id);
}
