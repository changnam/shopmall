package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.AccountDto;

public interface AccountService {
	AccountDto createAccount(AccountDto accountDto);
	List<AccountDto> getAllAccounts();
	Page<AccountDto> getPageAccounts(Pageable pageable);
	AccountDto updateAccount(Long accountId,AccountDto accountDto);
	void deleteAccountById(Long accountId);
	
}
