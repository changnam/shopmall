package com.honsoft.shopmall.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateRequest {
	private String roleId;
	private String roleName;
//	private String createdBy;
	
	private Set<String> permissionIds;
}
