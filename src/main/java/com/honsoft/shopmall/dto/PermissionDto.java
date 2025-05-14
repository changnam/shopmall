package com.honsoft.shopmall.dto;

import com.honsoft.shopmall.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {
	private String permissionId;

	private String name;
	private String path;
	private String httpMethod;

	private Role role;

}
