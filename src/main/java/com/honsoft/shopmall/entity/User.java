package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.List;

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
//      @GeneratedValue(strategy =GenerationType.IDENTITY)
        private String userId;

        private String password;
        private String name;

        @Column(unique = true)
        private String email;
        private Boolean enabled;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonManagedReference("user-userRoles")
        @OrderBy("assignedAt DESC") // or "role.roleId ASC", depending on your entity fields
    private List<UserRole> userRoles = new ArrayList(); //초기화 되어 있어야 add, clear 등 가능함

        public void addRole(Role role) {
            boolean exists = this.userRoles.stream()
                .anyMatch(ur -> ur.getRole().equals(role));

            if (!exists) {
                UserRole userRole = new UserRole(this, role);
                this.userRoles.add(userRole);
                role.addUserRole(userRole);
            }
        }

        public void clearRoles() {
                this.userRoles.clear();
        }
}