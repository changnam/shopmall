package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	private String customerId;
	private String name;
	private String phone;
	
//	@Builder.Default
	private List<AddressDto> addresses = new ArrayList<>();

}
