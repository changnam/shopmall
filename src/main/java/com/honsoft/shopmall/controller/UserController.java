package com.honsoft.shopmall.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		return "user/list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("userDto", new UserDto());
		model.addAttribute("roles", roleService.getAllRoles());
		return "user/form";
	}

	@PostMapping("/add")
	public String addUser(@Valid @ModelAttribute("userDto") UserCreateDto userCreateDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", roleService.getAllRoles());
			return "user/form";
		}
		userService.createUser(userCreateDto);
		return "redirect:/users";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable String id, Model model) {
		Optional<UserDto> userOpt = userService.findById(id);
		if (userOpt.isEmpty()) {
			return "redirect:/users";
		}
		model.addAttribute("userDto", userOpt.get());
		model.addAttribute("roles", roleService.getAllRoles());
		return "user/form";
	}

	@PostMapping("/edit/{id}")
	public String updateUser(@PathVariable String id, @Valid @ModelAttribute("userDto") UserUpdateDto userUpdateDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", roleService.getAllRoles());
			return "user/form";
		}
		userService.updateUser(id, userUpdateDto);
		return "redirect:/users";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		userService.deleteUserById(id);
		return "redirect:/users";
	}
}
