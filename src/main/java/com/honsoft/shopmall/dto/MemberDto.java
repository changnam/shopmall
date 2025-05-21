package com.honsoft.shopmall.dto;

import com.honsoft.shopmall.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	    private Long num;
	    
	    private String memberId;
	    
	    private String password;
	    
	    private String name;   
	      
	    private String phone;
	  
	    private String email;

	    private String address;

	    private MemberRole role;
}
