package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.AddressDto;
import com.honsoft.shopmall.dto.CustomerDto;
import com.honsoft.shopmall.entity.Address;
import com.honsoft.shopmall.entity.Customer;
import com.honsoft.shopmall.exception.NotFoundException;
import com.honsoft.shopmall.mapper.AddressMapper;
import com.honsoft.shopmall.mapper.CustomerMapper;
import com.honsoft.shopmall.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private final AddressMapper addressMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,AddressMapper addressMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.addressMapper = addressMapper;
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
	@Transactional
	public CustomerDto updateCustomer(String customerId, CustomerDto customerDto) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));

		// Update basic fields
		customer.setName(customerDto.getName());

		// Clear and repopulate addresses (simple but safe way)
		customer.getAddresses().clear();  //주소만 따로 변경할지 여부에 따라서 적용 고려해야 함

		for (AddressDto addressDto : customerDto.getAddresses()) {
			Address address = addressMapper.toEntity(addressDto);
			address.setCustomer(customer); // very important
			customer.getAddresses().add(address);
		}

		Customer updatedCustomer = customerRepository.save(customer);
		return customerMapper.toDto(updatedCustomer);
	}

	@Override
	public void deleteCustomerById(String customerId) {
		customerRepository.deleteById(customerId);
	}

}
