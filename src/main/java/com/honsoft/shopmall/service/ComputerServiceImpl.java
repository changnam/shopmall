package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.mapper.ComputerMapper;
import com.honsoft.shopmall.repository.ComputerRepository;

@Service
public class ComputerServiceImpl implements ComputerService {
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	private final ComputerRepository computerRepository;
	private final ComputerMapper computerMapper;

	public ComputerServiceImpl(ComputerRepository computerRepository, ComputerMapper computerMapper) {
		this.computerRepository = computerRepository;
		this.computerMapper = computerMapper;
	}

	@Override
	public List<ComputerDto> getByBrand(String brand) {
		List<Computer> computers = computerRepository.findAll();
		List<ComputerDto> dtos = computerMapper.toDtoList(computers);
		return dtos;
	}

	@Override
	public ComputerDto createComputer(ComputerDto computerDto) {
		Computer computer = computerMapper.toEntity(computerDto);
		Computer saved = computerRepository.save(computer);
		ComputerDto savedDto = computerMapper.toDto(saved);
		return savedDto;
	}

	@Override
	public List<ComputerDto> getAllComputers() {
		List<Computer> computers = computerRepository.findAll();
		List<ComputerDto> dtos = computerMapper.toDtoList(computers);
		return dtos;
	}

	@Override
	public Page<ComputerDto> getPageComputers(Pageable pageable) {
		Page<Computer> computers = computerRepository.findAll(pageable);
		Page<ComputerDto> dtos = computerMapper.toPage(computers);
		return dtos;
	}

}
