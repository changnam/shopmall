package com.honsoft.shopmall.dto;

import java.util.List;

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
	    
	    private int age;
	    
	    private Integer koreanAge;

	    private List<String> roles ; 

}
