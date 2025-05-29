package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.UserRoleAssignment;

@Repository
public interface UserRoleAssignmentRepository extends JpaRepository<UserRoleAssignment, Long>{
	  List<UserRoleAssignment> findByUser_UserId(String userId);
}
