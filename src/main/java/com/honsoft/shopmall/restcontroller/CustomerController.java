package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.CustomerDto;
import com.honsoft.shopmall.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private final CustomerService customerService;
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerDto>> getAllCustomers(){
		List<CustomerDto> list = customerService.getAllCustomers();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") String customerId){
		CustomerDto customerDto = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(customerDto);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
		CustomerDto createdCustomerDto = customerService.createCustomer(customerDto);
		return ResponseEntity.ok(createdCustomerDto);
	}
	
	@PutMapping
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto){
		CustomerDto updatedCustomerDto = customerService.updateCustomer(customerDto.getCustomerId(),customerDto);
		return ResponseEntity.ok(updatedCustomerDto);
	}
	
}
