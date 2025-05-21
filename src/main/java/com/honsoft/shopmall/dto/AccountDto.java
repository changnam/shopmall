package com.honsoft.shopmall.dto;

import java.util.HashSet;
import java.util.Set;

import com.honsoft.shopmall.entity.AccountRole;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	
	    private Long accountId;

	    private String nickname;

	    @Email
	    private String email;

	    private String password;

	    private Set<AccountRole> roles = new HashSet<>(); 

}
