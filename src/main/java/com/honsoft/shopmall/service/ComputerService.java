package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;

public interface ComputerService {
	ComputerDto createComputer(ComputerDto computerDto);
	List<ComputerDto> getAllComputers();
	Page<ComputerDto> getPageComputers(Pageable pageable);
	List<ComputerDto> getByBrand(String brand);
}
