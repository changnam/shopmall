package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.CustomerDto;
import com.honsoft.shopmall.entity.Customer;
import com.honsoft.shopmall.mapper.CustomerMapper;
import com.honsoft.shopmall.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	public CustomerServiceImpl(CustomerRepository customerRepository,CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}
	
	@Override
	public List<CustomerDto> getAllCustomers(){
		List<Customer> list = customerRepository.findAll();
		return customerMapper.toDtoList(list);
	}

}
