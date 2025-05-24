package com.honsoft.shopmall.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
//@AllArgsConstructor
public class User {
	@Id
//	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private String userId;

	private String password;
	private String name;
	
	@Column(unique = true)
	private String email;
	private Boolean enabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	@OrderBy("assignedAt DESC") // or "role.roleId ASC", depending on your entity fields
    private Set<UserRole> userRoles;

}
