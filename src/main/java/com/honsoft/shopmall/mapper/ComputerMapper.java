package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.entity.Computer;

//And for your subclass:
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductDetailMapper.class,ProductImageMapper.class,ReviewMapper.class})
public abstract class ComputerMapper {
	@Autowired
	protected ProductDetailMapper productDetailMapper;

	@Autowired
	protected ProductImageMapper productImageMapper;

	@Autowired
	protected ReviewMapper reviewMapper;
	//에러메시지 : 
	//Unmapped target property: "productId". Mapping from property "ProductDetail productDetail" to "ProductDetailDto productDetail".
	//원인: 
	//This means MapStruct is trying to map Computer.productDetail to ComputerDto.productDetail, 
	//but doesn't know how to map the nested product.productId into productDetailDto.productId.
	//
	//조치: ProductDetailMapper.class 를 uses 에 추가해준다.
	@Mapping(target = "productDetail", expression = "java(productDetailMapper.toDto(computer.getProductDetail()))")
	public abstract ComputerDto toDto(Computer computer);

	@Mapping(target = "productDetail", expression = "java(productDetailMapper.toEntity(dto.getProductDetail(), context))")
	public abstract Computer toEntity(ComputerDto dto,@Context MappingContext context);

	public abstract List<ComputerDto> toDtoList(List<Computer> computers);

//	List<Computer> toEntityList(List<ComputerDto> computerDtos);
	
	public Page<ComputerDto> toPage(Page<Computer> computers) {
		return computers.map(this::toDto);
	}
}