package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CleanerDto extends ProductDto{
	private String cleanerId;
    private String brand;
    private Integer powerRating;
    private CleanerDetailDto productDetail; // subclass of ProductDetailDto
}
