package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Optional;

import com.honsoft.shopmall.dto.RoleDto;

public interface RoleService {
	RoleDto createRole(RoleDto roleDto);
	List<RoleDto> getAllRoles() ;
	RoleDto getRoleById(String id);
	RoleDto updateRole(String id, RoleDto roleDto);
	void deleteRoleById(String id);
	void assignUserToRole(String roleId, String userId);
	void removeUserFromRole(String roleId, String userId);
	void assignPermissionToRole(String roleId, String permissionId);
	void removePermissionFromRole(String roleId, String permissionId);
}
