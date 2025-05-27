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

@Setter
@Getter
@Entity
@Table(name = "role_permissions")
@NoArgsConstructor
public class RolePermission {
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "roleId", column = @Column(name = "role_id")),
			@AttributeOverride(name = "permissionId", column = @Column(name = "permission_id")) })
	private RolePermissionId id;

	@ManyToOne
	@MapsId("roleId") // RolePermissionId 의 roleId 컬럼 값으로 사용
	@JoinColumn(name = "role_id") // This is crucial!
	private Role role;

	@ManyToOne
	@MapsId("permissionId")
	@JoinColumn(name = "permission_id") // This too!
	private Permission permission;

	private LocalDateTime assignedAt;

	public RolePermission(Role role, Permission permission) {
		this.role = role;
		this.permission = permission;
		this.id = new RolePermissionId(role.getRoleId(), permission.getPermissionId()); // 💥 Important!
		this.assignedAt = LocalDateTime.now(); // optional, if not handled elsewhere
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof RolePermission))
			return false;
		RolePermission that = (RolePermission) o;
		return this.id != null && this.id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
