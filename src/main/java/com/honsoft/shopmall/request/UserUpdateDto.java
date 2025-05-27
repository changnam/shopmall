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
public class UserUpdateDto {

	private String name;
	private String email;
	private Boolean enabled;

//    @Builder.Default //  이게 없으면 초기화시 roeIds 에 값을 안 준 경우 , null 로 초기화 한다. 
    private Set<String> roleIds; // avoid exposing full Role entity
}
