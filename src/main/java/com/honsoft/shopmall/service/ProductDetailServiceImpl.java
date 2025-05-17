package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.CleanerDetailDto;
import com.honsoft.shopmall.dto.ComputerDetailDto;
import com.honsoft.shopmall.dto.ProductDetailDto;
import com.honsoft.shopmall.entity.ProductDetail;
import com.honsoft.shopmall.mapper.ProductDetailMapper;
import com.honsoft.shopmall.repository.ProductDetailRepository;

@Service
public class ProductDetailServiceImpl implements ProductDetailService{
	
	private final ProductDetailRepository productDetailRepository;
	private final ProductDetailMapper productDetailMapper;
	
	public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository,ProductDetailMapper productDetailMapper) {
		this.productDetailRepository = productDetailRepository;
		this.productDetailMapper = productDetailMapper;
	}

	@Override
	public List<ProductDetailDto> getAllProductDetails() {
		List<ProductDetail> list = productDetailRepository.findAll();
		List<ProductDetailDto> dtos = productDetailMapper.toDtoList(list);
		return dtos;
	}

	@Override
	public Page<ProductDetailDto> getPageProducts(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComputerDetailDto> getAllComputerDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CleanerDetailDto> getAllCleanerDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDetailDto updateProduct(Long productId, ProductDetailDto productDetailDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductDetail(Long productDetailId) {
		// TODO Auto-generated method stub
		
	}

}
