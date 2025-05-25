package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
	private String userId;
	private String password;
	private String name;
	private String email;
	private Boolean enabled;

//	@Builder.Default
	private List<RoleDto> roles;
}
