package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;

public interface ProductService {
//	ProductDto createProduct(ProductDto productDto);
	List<ProductDto> getAllProducts();
	Page<ProductDto> getPageProducts(Pageable pageable);
	List<ComputerDto> getAllComputers();
	List<CleanerDto> getAllCleaners();
//	ProductDto getProductById(Long productId);
	ProductDto updateProduct(Long productId,ProductDto productDto);
	
}
