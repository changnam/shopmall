package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.entity.Computer;

//And for your subclass:
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductDetailMapper.class,ProductImageMapper.class,ReviewMapper.class})
public interface ComputerMapper {
	//에러메시지 : 
	//Unmapped target property: "productId". Mapping from property "ProductDetail productDetail" to "ProductDetailDto productDetail".
	//원인: 
	//This means MapStruct is trying to map Computer.productDetail to ComputerDto.productDetail, 
	//but doesn't know how to map the nested product.productId into productDetailDto.productId.
	//
	//조치: ProductDetailMapper.class 를 uses 에 추가해준다.
	ComputerDto toDto(Computer computer);

	Computer toEntity(ComputerDto dto);

	List<ComputerDto> toDtoList(List<Computer> computers);

	List<Computer> toEntityList(List<ComputerDto> computerDtos);
	
	default Page<ComputerDto> toPage(Page<Computer> computers) {
		return computers.map(this::toDto);
	}
}