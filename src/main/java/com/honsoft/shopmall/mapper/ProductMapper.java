package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;

@Mapper(componentModel = "spring", uses = { ProductDetailMapper.class, ProductImageMapper.class , ReviewMapper.class, ComputerMapper.class, CleanerMapper.class})
public abstract class ProductMapper {

	@SubclassMapping(source = Computer.class, target = ComputerDto.class)
	@SubclassMapping(source = Cleaner.class, target = CleanerDto.class)
	public abstract ProductDto toDto(Product product);

	@SubclassMapping(source = ComputerDto.class, target = Computer.class)
	@SubclassMapping(source = CleanerDto.class, target = Cleaner.class)
	public abstract Product toEntity(ProductDto productDto);
	
	public Page<ProductDto> toPage(Page<Product> products) {
		return products.map(this::toDto);
	}
	
	public abstract List<ProductDto> toDtoList(List<Product> products);
	public abstract List<Product> toEntityList(List<ProductDto> productDtos);

//	public List<ProductDto> toDtoList(List<Product> products) {
//		return products.stream().map(this::toDto).toList();
//	}
}
