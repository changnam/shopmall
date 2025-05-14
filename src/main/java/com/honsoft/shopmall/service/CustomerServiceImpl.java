package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.CustomerDto;
import com.honsoft.shopmall.entity.Address;
import com.honsoft.shopmall.entity.Customer;
import com.honsoft.shopmall.exception.NotFoundException;
import com.honsoft.shopmall.mapper.CustomerMapper;
import com.honsoft.shopmall.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> list = customerRepository.findAll();
		return customerMapper.toDtoList(list);
	}

	@Override
	public CustomerDto getCustomerById(String customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException(customerId + " not found"));
//		Author author = authorRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Author not found"));
		return customerMapper.toDto(customer);
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = customerMapper.toEntity(customerDto);

		// Make sure each address has the back-reference to customer
//	    for (Address address : customer.getAddresses()) {
//	        address.setCustomer(customer);
//	    }

		logger.info("customer: {}", customer);
		for (Address address : customer.getAddresses()) {
			logger.info("address name: {} customerID: {}", address.getAddressName(),
					address.getCustomer().getCustomerId());
		}
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDto savedCustomerDto = customerMapper.toDto(savedCustomer);
		return savedCustomerDto;
	}

	@Override
	public CustomerDto updateCustomer(String customerId, CustomerDto customerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomerById(String customerId) {
		// TODO Auto-generated method stub

	}

}
