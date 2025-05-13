package com.honsoft.shopmall.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
	private Long addressId;
	private String country;
	private Long customerId;
}
