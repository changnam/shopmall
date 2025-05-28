package com.honsoft.shopmall.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;
import com.honsoft.shopmall.service.RoleService;
import com.honsoft.shopmall.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@GetMapping
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users/userList";
	}
	
	@GetMapping("/page")
	public String pageUsers(Model model,@PageableDefault(page = 0, size = 2, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		
		String sort = pageable.getSort().stream()
			    .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
			    .collect(Collectors.joining("&sort=")); // Note: '&sort=' for multiple sort criteria
		
		Page<UserDto> pageUser = userService.getPageUsers(pageable);
		model.addAttribute("users", pageUser.getContent());
		model.addAttribute("page", pageUser);
		model.addAttribute("sort", sort);
		return "users/userPage";
	}
	

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("userCreateDto", new UserCreateDto());
		model.addAttribute("roles", roleService.getAllRoles());
		return "users/userAddForm";
	}

	@PostMapping("/add")
	public String addUser(@Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", roleService.getAllRoles());
			return "users/userAddForm";
		}
		userService.createUser(userCreateDto);
		return "redirect:/users";
	}

	@GetMapping("/{id}")
	public String getUserById(@PathVariable("id") String id, Model model) {
		Optional<UserDto> userOpt = userService.findById(id);
		if (userOpt.isEmpty()) {
			return "redirect:/users";
		}
		model.addAttribute("userDto", userOpt.get());
		model.addAttribute("roles", roleService.getAllRoles());
		return "users/userDetail";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		Optional<UserDto> userOpt = userService.findById(id);
		if (userOpt.isEmpty()) {
			return "redirect:/users";
		}
		model.addAttribute("userUpdateDto", userOpt.get());
		model.addAttribute("roles", roleService.getAllRoles());
		return "users/userEditForm";
	}

	@PutMapping("/edit/{id}")
	public String updateUser(@PathVariable("id") String id, @Valid @ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", roleService.getAllRoles());
			return "users/userEditForm";
		}
		userService.updateUser(id, userUpdateDto);
		return "redirect:/users";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") String id) {
		userService.deleteUserById(id);
		return "redirect:/users";
	}
}
