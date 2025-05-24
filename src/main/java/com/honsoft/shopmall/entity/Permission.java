package com.honsoft.shopmall.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions")
public class Permission {

	@Id
	private String  permissionId;
	
	private String name;
	private String path;
	
	@Pattern(regexp = "GET|POST|DELETE|PUT")
	private String httpMethod;
	
//	@ManyToMany(mappedBy = "permissions")
//	private Set<Role> roles;

	@OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
	@JsonManagedReference
    private Set<RolePermission> rolePermissions;
}
