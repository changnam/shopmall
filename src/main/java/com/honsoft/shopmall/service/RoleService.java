package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.request.RoleCreateRequest;
import com.honsoft.shopmall.request.RoleUpdateDto;

public interface RoleService {
	RoleDto createRole(RoleCreateRequest roleCreateRequest);
	Page<RoleDto> getAllRoles(Pageable pageable) ;
	List<RoleDto> getAllRoles();
	RoleDto getRoleById(String id);
	RoleDto updateRole(String id, RoleUpdateDto roleUpdateDto);
	void deleteRoleById(String id);
	void assignUserToRole(String roleId, String userId);
	void removeUserFromRole(String roleId, String userId);
	void assignPermissionToRole(String roleId, String permissionId);
	void removePermissionFromRole(String roleId, String permissionId);
}
