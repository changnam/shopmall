package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
//@AllArgsConstructor
public class Role {
        @Id
        private String roleId;

        private String roleName;

//      @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//      @JoinTable(name="role_permissions",joinColumns = @JoinColumn(name="role_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
//      private List<Permission> permissions;
//
        @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//      @JsonManagedReference("role-rolePermissions")
        private List<RolePermission> rolePermissions = new ArrayList<>();

        @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//      @JsonManagedReference("role-userRoles")
        private List<UserRole> userRoles = new ArrayList<>();

        public void addUserRole(UserRole userRole) {
            this.userRoles.add(userRole);
        }

}