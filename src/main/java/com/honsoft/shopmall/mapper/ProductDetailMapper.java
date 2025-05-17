package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDetailDto;
import com.honsoft.shopmall.dto.ComputerDetailDto;
import com.honsoft.shopmall.dto.ProductDetailDto;
import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.entity.CleanerDetail;
import com.honsoft.shopmall.entity.ComputerDetail;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.entity.ProductDetail;

@Mapper(componentModel = "spring")
public abstract class ProductDetailMapper {
    @SubclassMapping(source = ComputerDetail.class, target = ComputerDetailDto.class)
    @SubclassMapping(source = CleanerDetail.class, target = CleanerDetailDto.class)
    @Mapping(source = "product.productId", target = "productId")
    public abstract ProductDetailDto toDto(ProductDetail detail);
    
    public abstract List<ProductDetailDto> toDtoList(List<ProductDetail> productDetails);
    
    public Page<ProductDetailDto> toPage(Page<ProductDetail> productDetails){
    	return productDetails.map(this::toDto);
    }
}