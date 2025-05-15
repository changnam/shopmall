package com.honsoft.shopmall.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
//	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private String userId;

	private String password;
	private String name;
	private String email;
	private Boolean enabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	@OrderBy("assignedAt DESC") // or "role.roleId ASC", depending on your entity fields
    private List<UserRole> userRoles;

}
