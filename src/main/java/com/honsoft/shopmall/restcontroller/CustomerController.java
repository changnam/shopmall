package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
