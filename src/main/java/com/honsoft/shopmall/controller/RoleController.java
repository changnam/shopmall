package com.honsoft.shopmall.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.request.RoleCreateRequest;
import com.honsoft.shopmall.service.PermissionService;
import com.honsoft.shopmall.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

	private final RoleService roleService;
	private final PermissionService permissionService;
	
	public RoleController(RoleService roleService,PermissionService permissionService) {
		this.roleService = roleService;
		this.permissionService = permissionService;
	}

	@GetMapping
	public String getAllRoles(Model m,
			@PageableDefault(page = 0, size = 5, sort = "roleName", direction = Sort.Direction.ASC) Pageable pageable) {

		String sort = pageable.getSort().stream()
				.map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
				.collect(Collectors.joining("&sort="));

		Page<RoleDto> pageList = roleService.getAllRoles(pageable);
		m.addAttribute("roles", pageList.getContent());
		m.addAttribute("page", pageList);
		m.addAttribute("sort", sort);
		return "roles/roleList";
	}

	@GetMapping("/add")
	public String getCreateRoleForm(Model m) {

		List<PermissionDto> permissions = permissionService.getAllPermissions();
		
		m.addAttribute("roleCreateRequest", new RoleCreateRequest());
		m.addAttribute("permissions",permissions);
		
		return "roles/roleAddForm";
	}

	@PostMapping("/add")
	public String createRole(Model m, @Validated @ModelAttribute("roleCreteDto") RoleCreateRequest roleCreateRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "roles/roleAddForm";
		}
		
		RoleDto created = roleService.createRole(roleCreateRequest);

		return "redirect:/roles";
	}

}
