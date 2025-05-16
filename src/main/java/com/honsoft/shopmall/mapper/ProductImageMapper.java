package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.ProductImageDto;
import com.honsoft.shopmall.entity.ProductImage;

@Mapper(componentModel = "spring")
interface ProductImageMapper {
    ProductImageDto toDto(ProductImage image);
    ProductImage toEntity(ProductImageDto dto);
    
    List<ProductImageDto> toDtoList(List<ProductImage> productImages);
    List<ProductImage> toEntityList(List<ProductImageDto> productImageDtos);
}
