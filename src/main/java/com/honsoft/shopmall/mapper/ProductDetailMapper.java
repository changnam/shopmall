package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDetailDto;
import com.honsoft.shopmall.dto.ComputerDetailDto;
import com.honsoft.shopmall.dto.ProductDetailDto;
import com.honsoft.shopmall.entity.CleanerDetail;
import com.honsoft.shopmall.entity.ComputerDetail;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.entity.ProductDetail;
import com.honsoft.shopmall.repository.ProductRepository;

import jakarta.persistence.EntityExistsException;

@Mapper(componentModel = "spring", uses = {ComputerDetailMapper.class,CleanerDetailMapper.class})
public abstract class ProductDetailMapper {
	@Autowired
	protected ProductRepository productRepository;
	
    @SubclassMapping(source = ComputerDetail.class, target = ComputerDetailDto.class)
    @SubclassMapping(source = CleanerDetail.class, target = CleanerDetailDto.class)
    @Mapping(source = "product.productId", target = "productId")
    public abstract ProductDetailDto toDto(ProductDetail productDetail);
    
    @SubclassMapping(source = ComputerDetailDto.class, target = ComputerDetail.class)
    @SubclassMapping(source = CleanerDetailDto.class, target = CleanerDetail.class)
    @Mapping(target = "product", ignore = true)
    public abstract ProductDetail toEntity(ProductDetailDto productDetailDto);
    
    public abstract List<ProductDetailDto> toDtoList(List<ProductDetail> productDetails);
    public abstract List<ProductDetail> toEntityList(List<ProductDetailDto> productDetailDtos);
    
    public Page<ProductDetailDto> toPage(Page<ProductDetail> productDetails){
    	return productDetails.map(this::toDto);
    }
    
    @AfterMapping
	protected void afterToEntity(ProductDetailDto productDetailDto, @MappingTarget ProductDetail productDetail) {
		Product product = productRepository.findById(productDetailDto.getProductId())
				.orElseThrow(() -> new EntityExistsException(productDetailDto.getProductId() + " not found"));
		productDetail.setProduct(product);
	}
}