package com.honsoft.shopmall.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDto {
	private String roleId;
	private String roleName;
	
	private Set<String> permissionIds;
}
