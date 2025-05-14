package com.honsoft.shopmall.service;

import java.util.List;

import com.honsoft.shopmall.dto.CustomerDto;

public interface CustomerService {
	
	List<CustomerDto> getAllCustomers();
	CustomerDto getCustomerById(String customerId);
	CustomerDto createCustomer(CustomerDto customerDto);
	CustomerDto updateCustomer(String customerId, CustomerDto customerDto);
	void deleteCustomerById(String customerId);
}
