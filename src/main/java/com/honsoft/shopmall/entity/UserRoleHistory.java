package com.honsoft.shopmall.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user_role_histories")
@NoArgsConstructor
public class UserRoleHistory {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Role role;

	private LocalDateTime changedAt;

	@Enumerated(EnumType.STRING)
	private ChangeType changeType; // ADDED, REMOVED

	private String changedBy;

	public UserRoleHistory(User user, Role role, LocalDateTime changedAt, ChangeType changeType, String changeBy) {
		this.user = user;
		this.role = role;
		this.changedAt = changedAt;
		this.changeType = changeType;
		this.changedBy = changeBy;

	}
}
