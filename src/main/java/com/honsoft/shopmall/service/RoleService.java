package com.honsoft.shopmall.service;

import java.util.List;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.request.RoleCreateDto;
import com.honsoft.shopmall.request.RoleUpdateDto;

public interface RoleService {
	RoleDto createRole(RoleCreateDto roleCreateDto);
	List<RoleDto> getAllRoles() ;
	RoleDto getRoleById(String id);
	RoleDto updateRole(String id, RoleUpdateDto roleUpdateDto);
	void deleteRoleById(String id);
	void assignUserToRole(String roleId, String userId);
	void removeUserFromRole(String roleId, String userId);
	void assignPermissionToRole(String roleId, String permissionId);
	void removePermissionFromRole(String roleId, String permissionId);
}
