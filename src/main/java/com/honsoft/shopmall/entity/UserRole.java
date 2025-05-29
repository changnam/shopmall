package com.honsoft.shopmall.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
//@Entity
@Table(name = "user_roles")
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = { "user", "role" })
public class UserRole extends BaseEntity<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
//	@MapsId("userId") // RolePermissionId 의 roleId 컬럼 값으로 사용
	@JoinColumn(name = "user_id") // This is crucial!
	private User user;

	@ManyToOne
//	@MapsId("roleId")
	@JoinColumn(name = "role_id") // This too!
	private Role role;

	private LocalDateTime assignedAt;
	private String assignedBy;

	private boolean active; // if false, role is "removed" or "expired"
	
	public UserRole(User user, Role role, LocalDateTime assignedAt, String assignedBy, boolean active) {
		this.user = user;
		this.role = role;
		this.assignedAt = assignedAt;
		this.assignedBy = assignedBy;
		this.active = active;
	}

}