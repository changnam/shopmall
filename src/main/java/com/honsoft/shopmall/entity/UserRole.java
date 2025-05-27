package com.honsoft.shopmall.entity;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Entity
@Table(name = "user_roles")
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = { "user", "role" })
public class UserRole extends BaseEntity<String> {
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id")),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id")) })
	private UserRoleId id;

	@ManyToOne
	@MapsId("userId") // RolePermissionId 의 roleId 컬럼 값으로 사용
	@JoinColumn(name = "user_id") // This is crucial!
	private User user;

	@ManyToOne
	@MapsId("roleId")
	@JoinColumn(name = "role_id") // This too!
	private Role role;

	private LocalDateTime assignedAt;

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
		this.id = new UserRoleId(user.getUserId(), role.getRoleId()); // 💥 Important!
		this.assignedAt = LocalDateTime.now(); // optional, if not handled elsewhere
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserRole))
			return false;
		UserRole that = (UserRole) o;
		return this.id != null && this.id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}