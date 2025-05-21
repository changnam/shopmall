package com.honsoft.shopmall.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.service.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping
	public String createAccount(@RequestBody AccountDto accountDto){
		AccountDto created = accountService.createAccount(accountDto);
		return "accounts/accountAdd";
	}

	@GetMapping
	public String getAllAccounts(Model m){
		List<AccountDto> dtoList = accountService.getAllAccounts();
		m.addAttribute("accounts",dtoList);
		return "accounts/accountList";
	}
	
}
