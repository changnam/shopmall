package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
	private String roleId;

	private String name;

	@Builder.Default
	private List<PermissionDto> permissions = new ArrayList<>();

	@Builder.Default
	private List<UserRoleDto> userRoles = new ArrayList<>();
}
