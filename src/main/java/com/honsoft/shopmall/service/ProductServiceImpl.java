package com.honsoft.shopmall.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.mapper.CleanerMapper;
import com.honsoft.shopmall.mapper.ComputerMapper;
import com.honsoft.shopmall.mapper.ProductDetailMapper;
import com.honsoft.shopmall.mapper.ProductImageMapper;
import com.honsoft.shopmall.mapper.ProductMapper;
import com.honsoft.shopmall.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ProductImageMapper productImageMapper;
	private final ProductDetailMapper productDetailMapper;
	private final ComputerMapper computerMapper;
	private final CleanerMapper cleanerMapper;
	private final BizExceptionMessageService bizExceptionMessageService;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
			ProductImageMapper productImageMapper, ProductDetailMapper productDetailMapper,ComputerMapper computerMapper, CleanerMapper cleanerMapper,BizExceptionMessageService bizExceptionMessageService) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.productImageMapper = productImageMapper;
		this.productDetailMapper = productDetailMapper;
		this.computerMapper = computerMapper;
		this.cleanerMapper = cleanerMapper;
		this.bizExceptionMessageService = bizExceptionMessageService;
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

	@Transactional
	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) {
		Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId+ " not found"));
		
		product.setCategory(productDto.getCategory());
		product.setImages(productImageMapper.toEntityList(productDto.getImages()));
		product.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		product.setLastModifiedDate(LocalDateTime.now());
		product.setName(productDto.getName());
		product.setProductCondition(productDto.getProductCondition());
		product.setProductDetail(productDetailMapper.toEntity(productDto.getProductDetail()));
		product.setUnitPrice(productDto.getUnitPrice());
		product.setUnitsInStock(productDto.getUnitsInStock());
		product.setUpdatedAt(Instant.now());
		
		Product updated = productRepository.save(product);
		ProductDto dto = productMapper.toDto(updated);
		return dto;
	}

	@Transactional
	@Override
	public void deleteProduct(Long productId) {
		Product existing = productRepository.findById(productId).orElseThrow(() -> bizExceptionMessageService.createLocalizedException("PRODUCT_NOT_FOUND"));
		productRepository.delete(existing);
	}

}
