package com.honsoft.shopmall.dto;

import java.util.HashSet;
import java.util.Set;

import com.honsoft.shopmall.entity.Role;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private Long accountId;
	private String password;
	private String address;
	private String gender;
	private String hobbies;
	private String welcomeWords;
    private String name;
	private String nickname;
	private String email;

}
