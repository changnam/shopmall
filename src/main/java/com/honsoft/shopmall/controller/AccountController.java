package com.honsoft.shopmall.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honsoft.shopmall.dto.AccountDto;
import com.honsoft.shopmall.dto.AccountRoleDto;
import com.honsoft.shopmall.service.AccountRoleService;
import com.honsoft.shopmall.service.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
	private final AccountRoleService accountRoleService;

	public AccountController(AccountService accountService, AccountRoleService accountRoleService) {
		this.accountService = accountService;
		this.accountRoleService = accountRoleService;
	}

	@PostMapping("/add")
	public String addAccount(@ModelAttribute AccountDto dto) {
	    AccountDto added = accountService.createAccount(dto); // implement in service
	    return "redirect:/accounts";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteAccount(@PathVariable("id") Long id) {
	    accountService.deleteAccountById(id); // implement in service
	    return "redirect:/accounts";
	}
	
	@GetMapping
	public String getAllAccounts(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "email,asc") String sort, Model m) {

		String[] sortParts = sort.split(",");
		String sortField = sortParts[0];
		String sortDir = sortParts.length > 1 ? sortParts[1] : "asc";

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
		// List<AccountDto> dtoList = accountService.getAllAccounts();
		Page<AccountDto> dtoPage = accountService.getPageAccounts(pageable);

		m.addAttribute("accounts", dtoPage.getContent());
		m.addAttribute("page", dtoPage);
		m.addAttribute("sort", sort); // keep track of sorting in template
		m.addAttribute("sortField", sortField);
		m.addAttribute("sortDir", sortDir);
		return "accounts/accountList";
	}

	@GetMapping("/edit/{id}")
	public String updateAccount(Model m, @PathVariable("id") Long accountId) {
		AccountDto existing = accountService.getAccountById(accountId);
		List<AccountRoleDto> allRoles = accountRoleService.getAllAccountRoles();
		m.addAttribute("account", existing);
		m.addAttribute("allRoles", allRoles);
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
