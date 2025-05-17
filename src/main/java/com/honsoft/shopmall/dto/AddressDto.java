package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
	private Long addressId;
	private String addressName;
	private String country;
	private String customerId;
	private String zipCode;
	private String detailName;
}
