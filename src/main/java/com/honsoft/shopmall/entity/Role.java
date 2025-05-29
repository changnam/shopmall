package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Table(name = "roles")
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = { "roleAssignments", "rolePermissions" })
public class Role extends BaseEntity<String> {
	@Id
	private String roleId;

	private String roleName;

//      @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//      @JoinTable(name="role_permissions",joinColumns = @JoinColumn(name="role_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
//      private List<Permission> permissions;
//
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
//      @JsonManagedReference("role-rolePermissions")
	private List<RolePermission> rolePermissions = new ArrayList<>();

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
//      @JsonManagedReference("role-userRoles")
	private List<UserRoleAssignment> roleAssignments ;


}