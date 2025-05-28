package com.honsoft.shopmall.dto;

import java.util.List;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
	private String permissionId;

	private String name;
	private String path;
	
	@Pattern(regexp = "GET|POST|DELETE|PUT")
	private String httpMethod;
	
	private List<String> roleIds ;

}
