package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRoleHistory;

@Repository
public interface UserRoleHistoryRepository extends JpaRepository<UserRoleHistory, Long> {
	List<UserRoleHistory> findByUser(User user);
	List<UserRoleHistory> findByRole(Role role);
}
