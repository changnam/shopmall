package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;

@Mapper(componentModel = "spring", uses = { ProductDetailMapper.class, ProductImageMapper.class , ReviewMapper.class})
public abstract class ProductMapper {

	@Autowired
	protected ComputerMapper computerMapper;

	@Autowired
	protected CleanerMapper cleanerMapper;

	public ProductDto toDto(Product product) {
		if (product instanceof Computer computer) {
			return computerMapper.toDto(computer);
		} else if (product instanceof Cleaner cleaner) {
			return cleanerMapper.toDto(cleaner);
		} else {
			throw new IllegalArgumentException("Unsupported product type: " + product.getClass());
		}
	}

	public Page<ProductDto> toPage(Page<Product> products) {
		return products.map(this::toDto);
	}

	public List<ProductDto> toDtoList(List<Product> products) {
		return products.stream().map(this::toDto).toList();
	}
}
