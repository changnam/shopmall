package com.honsoft.shopmall.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignRoleDto {
	private String userId;
	private List<String> roleIds;
}
