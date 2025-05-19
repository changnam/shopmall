package com.honsoft.shopmall.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Object> createAccount(@RequestBody AccountDto accountDto){
		AccountDto created = accountService.createAccount(accountDto);
		return ResponseHandler.responseBuilder("account created", HttpStatus.OK, created);
	}

}
