package com.honsoft.shopmall.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
//	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//	@JoinTable(name="role_permissions",joinColumns = @JoinColumn(name="role_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
//	private List<Permission> permissions;
//	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<RolePermission> rolePermissions;
    
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<UserRole> userRoles;
}