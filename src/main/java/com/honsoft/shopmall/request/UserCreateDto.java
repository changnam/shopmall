package com.honsoft.shopmall.request;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class UserCreateDto {
	private String userId;

	private String password;
	private String name;
	private String email;
	private Boolean enabled;

//    @Builder.Default
    private Set<String> roleIds; // avoid exposing full Role entity

}
