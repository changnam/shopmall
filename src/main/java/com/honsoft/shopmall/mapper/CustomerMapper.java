package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.CustomerDto;
import com.honsoft.shopmall.entity.Customer;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {
	
	CustomerDto toDto(Customer customer);
	
	Customer toEntity(CustomerDto customerDto);
	
	List<CustomerDto> toDtoList(List<Customer> customers);
	
	List<Customer> toEntityList(List<CustomerDto> customerDtos);

}
