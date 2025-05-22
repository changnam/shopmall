package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<Object> createAccount(@RequestBody AccountDto accountDto) {
		AccountDto created = accountService.createAccount(accountDto);
		return ResponseHandler.responseBuilder("account created", HttpStatus.OK, created);
	}

	@GetMapping
	public ResponseEntity<Object> getAllAccounts(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "email,asc") String sort) {

		String[] sortParts = sort.split(",");
		String sortField = sortParts[0];
		String sortDir = sortParts.length > 1 ? sortParts[1] : "asc";

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
		// List<AccountDto> dtoList = accountService.getAllAccounts();
		Page<AccountDto> dtoPage = accountService.getPageAccounts(pageable);

//		List<AccountDto> dtoList = accountService.getAllAccounts();
		return ResponseHandler.responseBuilder("accounts selected", HttpStatus.OK, dtoPage);
	}
}
