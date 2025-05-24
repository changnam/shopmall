package com.honsoft.shopmall.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String userId;

	private String password;
	private String name;
	private String email;
	private Boolean enabled;

//    @Builder.Default
    private Set<String> roleIds; // avoid exposing full Role entity
}
