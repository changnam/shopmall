package com.honsoft.shopmall.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
	private String roleId;
	private String name;
	private List<String> permissionIds; // Optional if needed
}
