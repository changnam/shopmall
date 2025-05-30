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
@ToString(exclude = { "roleAssignments" })
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
	private List<UserRoleAssignment> roleAssignments ; // 초기화 되어 있어야 add, clear 등 가능함

	public void addRoleAssignment(UserRoleAssignment assignment) {
	    if (roleAssignments == null) {
	        roleAssignments = new ArrayList<>();
	    }
	    roleAssignments.add(assignment);
	}

	public void removeRoleAssignment(UserRoleAssignment assignment) {
	    if (roleAssignments != null) {
	        roleAssignments.remove(assignment);
	        assignment.setUser(null);
	        assignment.setRole(null);
	    }
	}

}