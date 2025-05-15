package com.honsoft.shopmall.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
	private String permissionId;

	private String name;
	private String path;
	private String httpMethod;

}
