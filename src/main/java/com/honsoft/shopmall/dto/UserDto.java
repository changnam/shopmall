package com.honsoft.shopmall.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String userId;

	private String name;
	private String email;
	private Boolean enabled;

//	@Builder.Default
	private List<RoleDto> roles;
}
