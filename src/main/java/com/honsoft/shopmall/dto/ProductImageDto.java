package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {
	private Integer idx;
	private String fileName;
    // Add more fields based on ProductDetail entity
}
