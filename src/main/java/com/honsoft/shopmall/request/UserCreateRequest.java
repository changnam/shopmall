package com.honsoft.shopmall.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class UserCreateRequest {
	@Size(min = 3)
	private String userId;

	private String password;
	private String name;
	
	@Email
	private String email;
	private Boolean enabled;

//    @Builder.Default
    private Set<String> roleIds; // avoid exposing full Role entity

}
