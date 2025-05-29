package com.honsoft.shopmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	@EntityGraph(attributePaths = {"roleAssignments"})
	Optional<Role> findById(String roleId);
}
