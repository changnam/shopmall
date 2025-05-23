package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.entity.Account;
import com.honsoft.shopmall.entity.AccountRole;
import com.honsoft.shopmall.entity.ERole;
import com.honsoft.shopmall.mapper.AccountMapper;
import com.honsoft.shopmall.mapper.AccountRoleMapper;
import com.honsoft.shopmall.repository.AccountRepository;
import com.honsoft.shopmall.repository.AccountRoleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class AccountServiceImpl implements AccountService {

	private final BizExceptionMessageService bizExceptionMessageService;
	private final AccountRepository accountRepository;
	private final AccountRoleRepository accountRoleRepository;
	private final AccountMapper accountMapper;
	private final AccountRoleMapper accountRoleMapper;
	private final PasswordEncoder passwordEncoder;
	private final Validator validator;

	public AccountServiceImpl(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository,
			AccountMapper accountMapper, AccountRoleMapper accountRoleMapper,
			BizExceptionMessageService bizExceptionMessageService, PasswordEncoder passwordEncoder,
			Validator validator) {
		this.accountRepository = accountRepository;
		this.accountRoleRepository = accountRoleRepository;
		this.accountMapper = accountMapper;
		this.accountRoleMapper = accountRoleMapper;
		this.bizExceptionMessageService = bizExceptionMessageService;
		this.passwordEncoder = passwordEncoder;
		this.validator = validator;
	}

	@Transactional
	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		if (accountDto.getAccountId() != null ) {
			throw new RuntimeException("account 생성시 accountId 는 설정할 수 없습니다.");
		}
		Account account = accountMapper.toEntity(accountDto,accountRoleRepository);
		Set<ConstraintViolation<Account>> violations = validator.validate(account);
		if (!violations.isEmpty()) {
			// Log or handle validation messages
			throw new ConstraintViolationException(violations);
		}
		//check email already exists
		accountRepository.findByEmail(account.getEmail()).ifPresent(a -> {throw bizExceptionMessageService.createLocalizedException("EMAIL_ALREADY_EXIST");});

		account.setPassword(passwordEncoder.encode(account.getPassword()));
		AccountRole ar = accountRoleRepository.findByErole(ERole.ROLE_USER).orElseThrow(() -> bizExceptionMessageService.createLocalizedException("ROLE_USER_NOT_FOUND"));
		
		account.setRoles(Set.of(ar)); 
		Account saved = accountRepository.save(account);
		AccountDto savedDto = accountMapper.toDto(saved);
		savedDto.setPassword("");
		return savedDto;
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> list = accountRepository.findAll();
		List<AccountDto> dtoList = accountMapper.toDtoList(list);
		
		return dtoList;
	}

	@Override
	public Page<AccountDto> getPageAccounts(Pageable pageable) {
		Page<Account> accountPage = accountRepository.findAll(pageable);
		Page<AccountDto> dtoPage = accountMapper.toDtoPage(accountPage);
		return dtoPage;
	}

	@Transactional
	@Override
	public AccountDto updateAccount(Long accountId, AccountDto accountDto) {
		Account existing = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(accountId + " not found"));
//		Account updating = accountMapper.toEntity(accountDto,accountRoleRepository);
		
//		existing.setEmail(updating.getEmail());
//		existing.setNickname(updating.getNickname());
		// Use MapStruct with roleRepo as context
        accountMapper.updateAccountFromDto(accountDto, existing, accountRoleRepository);
		
		Set<ConstraintViolation<Account>> violations = validator.validate(existing);
		if (!violations.isEmpty()) {
			// Log or handle validation messages
			throw new ConstraintViolationException(violations);
		}
		
		Account updated = accountRepository.save(existing);
		AccountDto updatedDto = accountMapper.toDto(updated);
		return updatedDto;
	}

	@Transactional
	@Override
	public void deleteAccountById(Long accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow(()->new EntityNotFoundException(accountId + " not found"));
		accountRepository.delete(account);
	}

	@Override
	public AccountDto getAccountById(Long accountId) {
		Account existing = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(accountId+" not found"));
		AccountDto dto = accountMapper.toDto(existing);
		return dto;
	}

}
