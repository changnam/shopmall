package com.honsoft.shopmall.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateRequest {
	private String userId;
	private String password;

}
