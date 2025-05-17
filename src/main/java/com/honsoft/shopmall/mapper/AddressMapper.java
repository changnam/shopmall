package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.honsoft.shopmall.dto.AddressDto;
import com.honsoft.shopmall.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	
	@Mapping(source = "customer.customerId", target = "customerId")
	AddressDto toDto(Address address);
	
	@Mapping(source = "customerId", target = "customer.customerId")
	Address toEntity(AddressDto addressDto);
	
	List<AddressDto> toDtoList(List<Address> addresses);
	
//	List<Address> toEntityList(List<AddressDto> addressDtos);

}
