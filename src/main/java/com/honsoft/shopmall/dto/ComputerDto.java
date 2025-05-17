package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComputerDto extends ProductDto{
	private String computerId;
    private String brand;
    private String processor;
    private String ram;
    private String storage;
    private ComputerDetailDto productDetail; // subclass of ProductDetailDto
}
