package com.honsoft.shopmall.service;

import java.util.List;

import com.honsoft.shopmall.dto.PermissionDto;

public interface PermissionService {
	PermissionDto createPermission(PermissionDto permissionDto);
	PermissionDto getPermissionById(String permissionId);
	List<PermissionDto> getAllPermissions();
	PermissionDto updatePermission(String permissionId, PermissionDto permissionDto);
	void deletePermissionById(String permissionId);
	void assignRoleToPermission(String permissionId, String roleId);
	void removeRoleFromPermission(String permissionId, String roleId);
	Integer deleteAllPermissionsByRoleId(String roleId);
}
