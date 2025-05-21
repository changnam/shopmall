package com.honsoft.shopmall.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.dto.AccountRoleDto;
import com.honsoft.shopmall.service.AccountRoleService;
import com.honsoft.shopmall.service.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
	private final AccountRoleService accountRoleService;

	public AccountController(AccountService accountService,AccountRoleService accountRoleService) {
		this.accountService = accountService;
		this.accountRoleService = accountRoleService;
	}

	@PostMapping
	public String createAccount(@RequestBody AccountDto accountDto) {
		AccountDto created = accountService.createAccount(accountDto);
		return "accounts/accountAdd";
	}

	@GetMapping
	public String getAllAccounts(Model m, @PageableDefault(page = 0, size = 10, sort = "email", direction = Sort.Direction.ASC) Pageable pageable ) {
//		List<AccountDto> dtoList = accountService.getAllAccounts();
		Page<AccountDto> dtoPage = accountService.getPageAccounts(pageable);
		
		m.addAttribute("accounts", dtoPage.getContent());
	    m.addAttribute("page", dtoPage);
	    
		return "accounts/accountList";
	}

	@GetMapping("/edit/{id}")
	public String updateAccount(Model m, @PathVariable("id") Long accountId) {
		AccountDto existing = accountService.getAccountById(accountId);
		List<AccountRoleDto> allRoles =  accountRoleService.getAllAccountRoles();
		m.addAttribute("account", existing);
		m.addAttribute("allRoles",allRoles);
		return "accounts/accountEdit";
	}

	@PutMapping("/edit/{id}")
	public String updateAccount(Model m, @PathVariable("id") Long accountId, @Validated AccountDto accountDto,
			BindingResult bindingResult) {
		AccountDto updated = accountService.updateAccount(accountId, accountDto);

		if (bindingResult.hasErrors())
			return "accounts/accountEdit";

		m.addAttribute("account", updated);
		return "accounts/accountEdit";
//		return "redirect:/accounts";
	}

}
