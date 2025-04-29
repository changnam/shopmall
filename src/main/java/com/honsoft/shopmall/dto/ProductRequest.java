package com.honsoft.shopmall.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(@NotBlank @Size(min = 4, max = 10, message="4자 ~ 10자 이내로 입력해주세요") String name,

		@Min(value = 0) int price) {
	
	public ProductRequest(String name) {
		this(name,0);
	}
	
	public ProductRequest() {
		this("");
	}
}
