package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.entity.Cleaner;

//And for your subclass:
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductDetailMapper.class,ProductImageMapper.class,ReviewMapper.class})
public abstract class CleanerMapper {
	
	@Autowired
	protected ProductDetailMapper productDetailMapper;

	@Autowired
	protected ProductImageMapper productImageMapper;

	@Autowired
	protected ReviewMapper reviewMapper;
	
	@Mapping(target = "productDetail", expression = "java(productDetailMapper.toDto(cleaner.getProductDetail()))")
	public abstract CleanerDto toDto(Cleaner cleaner);

	@Mapping(target = "productDetail", expression = "java(productDetailMapper.toEntity(dto.getProductDetail(), context))")
    public abstract Cleaner toEntity(CleanerDto dto, @Context MappingContext context);
	
	public abstract List<CleanerDto> toDtoList(List<Cleaner> cleaners);

//	public abstract List<Cleaner> toEntityList(List<CleanerDto> cleanerDtos);

	public Page<CleanerDto> toPage(Page<Cleaner> cleaners) {
		return cleaners.map(this::toDto);
	}
}