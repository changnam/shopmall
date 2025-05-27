package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = { "userRoles" })
public class User extends BaseEntity<String> {
	@Id
//      @GeneratedValue(strategy =GenerationType.IDENTITY)
	@Size(min = 3)
	private String userId;

//        @Size(min = 8,max = 16)
	private String password;
	private String name;

	@Column(unique = true)
	private String email;
	private Boolean enabled;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//        @JsonManagedReference("user-userRoles")
	@OrderBy("assignedAt DESC") // or "role.roleId ASC", depending on your entity fields
	private List<UserRole> userRoles = new ArrayList<UserRole>(); // 초기화 되어 있어야 add, clear 등 가능함

	public void clearRoles() {
		Iterator<UserRole> iterator = this.userRoles.iterator();
		while (iterator.hasNext()) {
			UserRole userRole = iterator.next();
			Role role = userRole.getRole();

			// Remove from Role's userRoles list
			if (role != null) {
				role.getUserRoles().remove(userRole);
			}

			// Break both sides of the bidirectional relationship
			userRole.setUser(null);
			userRole.setRole(null);

			iterator.remove(); // Remove from this user's userRoles
		}
	}

	public void addRole(Role role) {
		boolean exists = this.userRoles.stream().anyMatch(ur -> ur.getRole().equals(role));
		if (!exists) {
			UserRole userRole = new UserRole(this, role);
			this.userRoles.add(userRole);
			role.addUserRole(userRole);
		}
	}

	public void removeRole(Role role) {
		Iterator<UserRole> iterator = this.userRoles.iterator();
		while (iterator.hasNext()) {
			UserRole userRole = iterator.next();
			if (userRole.getRole().equals(role)) {
				// Break bidirectional relationship
				userRole.setUser(null);
				userRole.setRole(null);

				iterator.remove(); // Remove from user's userRoles list
				role.getUserRoles().remove(userRole); // Remove from role's userRoles list (if maintained)
				break; // Assuming only one matching UserRole should exist
			}
		}
	}
	
	public void removeUserRole(UserRole userRole) {
		this.userRoles.remove(userRole);
	}

	public void addUserRole(UserRole userRole) {
		this.userRoles.add(userRole);
	}
}