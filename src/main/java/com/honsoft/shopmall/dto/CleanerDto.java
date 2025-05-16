package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanerDto extends ProductDto{
	private String cleanerId;
    private String brand;
    private Double power;
}
