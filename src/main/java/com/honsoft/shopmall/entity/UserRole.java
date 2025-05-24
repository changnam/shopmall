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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column = @Column(name = "user_id")),
        @AttributeOverride(name = "roleId", column = @Column(name = "role_id"))
    })
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")  //RolePermissionId 의 roleId 컬럼 값으로 사용
    @JoinColumn(name = "user_id") // This is crucial!
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id") // This too!
    private Role role;

    private LocalDateTime assignedAt;
}

