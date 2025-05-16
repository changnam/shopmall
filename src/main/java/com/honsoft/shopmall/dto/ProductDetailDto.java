package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
	private String description;
	private Float weight;
	private Float height;
	private Float width;
    // Add more fields based on ProductDetail entity
}
