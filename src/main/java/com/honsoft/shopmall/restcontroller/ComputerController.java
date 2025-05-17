package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.ComputerService;

@RestController
@RequestMapping("/api/v1/computers")
public class ComputerController {
	
	private final ComputerService computerService;
	
	public ComputerController(ComputerService compouterService) {
		this.computerService = compouterService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createComputer(@RequestBody ComputerDto computerDto){
		ComputerDto created = computerService.createComputer(computerDto);
		return ResponseHandler.responseBuilder("computer created", HttpStatus.OK, created);
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllComputers() {
		List<ComputerDto> list = computerService.getAllComputers();
		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, list);
	}

	@GetMapping("/page")
	public ResponseEntity<Object> getPageComputers(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<ComputerDto> page = computerService.getPageComputers(pageable);
		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, page);
	}

}
