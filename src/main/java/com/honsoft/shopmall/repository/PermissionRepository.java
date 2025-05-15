package com.honsoft.shopmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{
	@Query(value = "delete from permissions where permissionid in (select permission_id from role_permissions where role_id = :roleId)", nativeQuery = true)
	@Modifying
	Integer deleteAllPermissionsByRoleId(@Param("roleId") String roleId);
	
}
