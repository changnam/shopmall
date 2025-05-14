package com.honsoft.shopmall.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	private String roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Permission> permissions;
	
	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private List<UserRole> userRoles;
    
}