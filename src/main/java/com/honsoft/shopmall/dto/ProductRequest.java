package com.honsoft.shopmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(@NotBlank @Size(min = 4, max = 10) String name,

		@Min(value = 0) int price, @Email String email) {
	
	public ProductRequest(String name) {
		this(name,0,"");
	}
	
	public ProductRequest() {
		this("");
	}
}
