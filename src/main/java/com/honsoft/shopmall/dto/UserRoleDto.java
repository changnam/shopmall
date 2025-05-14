package com.honsoft.shopmall.dto;

import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {
	private Long id;
	private User user;
	private Role role;

}
