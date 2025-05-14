package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.honsoft.shopmall.entity.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String userId;

	private String password;
	private String name;
	private String email;
	private Boolean enabled;

	@Builder.Default
	private List<UserRole> userRoles = new ArrayList<>();
}
