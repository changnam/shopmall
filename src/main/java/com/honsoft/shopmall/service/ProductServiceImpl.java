package com.honsoft.shopmall.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.mapper.CleanerMapper;
import com.honsoft.shopmall.mapper.ComputerMapper;
import com.honsoft.shopmall.mapper.ProductMapper;
import com.honsoft.shopmall.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ComputerMapper computerMapper;
	private final CleanerMapper cleanerMapper;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
			ComputerMapper computerMapper, CleanerMapper cleanerMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.computerMapper = computerMapper;
		this.cleanerMapper = cleanerMapper;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDto> dtos = productMapper.toDtoList(products);

		return dtos;
	}

	@Override
	public List<ComputerDto> getAllComputers() {
		List<Product> products = productRepository.findAll();

		List<Computer> computers = products.stream().filter(p -> p instanceof Computer).map(p -> (Computer) p)
				.collect(Collectors.toList());
		List<ComputerDto> dtos = computerMapper.toDtoList(computers);
		return dtos;
	}

	@Override
	public List<CleanerDto> getAllCleaners() {
		List<Product> products = productRepository.findAll();

		List<Cleaner> cleaners = products.stream().filter(p -> p instanceof Cleaner).map(p -> (Cleaner) p)
				.collect(Collectors.toList());
		List<CleanerDto> dtos = cleanerMapper.toDtoList(cleaners);
		return dtos;
	}

	@Override
	public Page<ProductDto> getPageProducts(Pageable pageable) {
		Page<Product> products = productRepository.findAll(pageable);
		Page<ProductDto> dtos = productMapper.toPage(products);
		return dtos;
	}

}
