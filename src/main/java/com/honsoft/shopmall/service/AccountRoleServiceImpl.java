package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.AccountRoleDto;
import com.honsoft.shopmall.entity.AccountRole;
import com.honsoft.shopmall.mapper.AccountRoleMapper;
import com.honsoft.shopmall.repository.AccountRoleRepository;

@Service
public class AccountRoleServiceImpl implements AccountRoleService {

	private final AccountRoleRepository accountRoleRepository;
	private final AccountRoleMapper accountRoleMapper;

	public AccountRoleServiceImpl(AccountRoleRepository accountRoleRepository, AccountRoleMapper accountRoleMapper) {
		this.accountRoleRepository = accountRoleRepository;
		this.accountRoleMapper = accountRoleMapper;
	}

	@Override
	public List<AccountRoleDto> getAllAccountRoles() {
		List<AccountRole> allRoles = accountRoleRepository.findAll();
		List<AccountRoleDto> allDtos = accountRoleMapper.toDtoList(allRoles);
		return allDtos;
	}

	@Override
	public AccountRoleDto createAccountRole(AccountRoleDto accountRoleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountRoleDto getAccountRoleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountRoleDto updateAccountRole(Long id, AccountRoleDto accountRoleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccountRoleById(Long id) {
		// TODO Auto-generated method stub

	}

}
