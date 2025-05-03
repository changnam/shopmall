package com.honsoft.shopmall.dto;

public record ProductDetailRequest(Long id, Float weight, Float height) {
	
	public ProductDetailRequest() {
		this(null, null, null);
	}
	
	public ProductDetailRequest(Float weight,Float height) {
		this(null,weight,height);
	}

}
