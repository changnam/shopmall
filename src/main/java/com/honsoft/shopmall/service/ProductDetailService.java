package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.CleanerDetailDto;
import com.honsoft.shopmall.dto.ComputerDetailDto;
import com.honsoft.shopmall.dto.ProductDetailDto;

public interface ProductDetailService {
//	ProductDto createProduct(ProductDto productDto);
	List<ProductDetailDto> getAllProductDetails();
	Page<ProductDetailDto> getPageProducts(Pageable pageable);
	List<ComputerDetailDto> getAllComputerDetails();
	List<CleanerDetailDto> getAllCleanerDetails();
//	ProductDto getProductById(Long productId);
	ProductDetailDto updateProduct(Long productId,ProductDetailDto productDetailDto);
	void deleteProductDetail(Long productDetailId);
	
}
