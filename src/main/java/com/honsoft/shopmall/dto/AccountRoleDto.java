package com.honsoft.shopmall.dto;

import com.honsoft.shopmall.entity.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleDto {
	private Long id;
	private ERole erole = ERole.ROLE_USER;
}
