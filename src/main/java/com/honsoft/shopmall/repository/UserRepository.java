package com.honsoft.shopmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	@EntityGraph(attributePaths = "userRoles")
	Optional<User> findByEmail(String email);
}
