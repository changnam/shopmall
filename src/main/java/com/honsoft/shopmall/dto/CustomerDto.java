package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import com.honsoft.shopmall.entity.Address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
	private String customerId;
	private String name;
	@Builder.Default
	private List<AddressDto> addresses = new ArrayList<>();

}
