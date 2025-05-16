package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.ProductDetailDto;
import com.honsoft.shopmall.entity.ProductDetail;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {
    ProductDetailDto toDto(ProductDetail detail);
    ProductDetail toEntity(ProductDetailDto dto);
    List<ProductDetailDto> toDtoList(List<ProductDetail> productDetails);
    List<ProductDetail> toEntityList(List<ProductDetailDto> productDetailDtos);
}