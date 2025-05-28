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
@ToString(exclude = { "userRoles", "rolePermissions" })
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
	private List<UserRole> userRoles = new ArrayList<>();

	public void addUserRole(UserRole userRole) {
		this.userRoles.add(userRole);
	}

	public void clearPermissions() {
		Iterator<RolePermission> iterator = this.rolePermissions.iterator();
		while (iterator.hasNext()) {
			RolePermission rolePermission = iterator.next();
			Permission permission = rolePermission.getPermission();

			// Remove from Role's userRoles list
			if (permission != null) {
				permission.getRolePermissions().remove(rolePermission);
			}

			// Break both sides of the bidirectional relationship
			rolePermission.setRole(null);
			rolePermission.setPermission(null);

			iterator.remove(); // Remove from this user's userRoles
		}
	}

	public void addPermission(Permission permission) {
		boolean exists = this.rolePermissions.stream().anyMatch(rp -> rp.getPermission().equals(permission));
		if (!exists) {
			RolePermission rolePermission = new RolePermission(this, permission);
			this.rolePermissions.add(rolePermission);
			permission.addRolePermission(rolePermission);
		}
	}

	public void removePermission(Permission permission) {
		Iterator<RolePermission> iterator = this.rolePermissions.iterator();
		while (iterator.hasNext()) {
			RolePermission rolePermission = iterator.next();
			if (rolePermission.getPermission().equals(permission)) {
				// Break bidirectional relationship
				rolePermission.setRole(null);
				rolePermission.setPermission(null);

				iterator.remove(); // Remove from user's userRoles list
				permission.getRolePermissions().remove(rolePermission); // Remove from role's userRoles list (if
																		// maintained)
				break; // Assuming only one matching UserRole should exist
			}
		}
	}

	public void removeRolePermission(RolePermission rolePermission) {
		this.rolePermissions.remove(rolePermission);
	}

	public void addRolePermission(RolePermission rolePermission) {
		this.rolePermissions.add(rolePermission);
	}
}