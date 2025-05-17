package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDetailDto;
import com.honsoft.shopmall.entity.CleanerDetail;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.repository.ProductRepository;

import jakarta.persistence.EntityExistsException;

@Mapper(componentModel = "spring")
public abstract class CleanerDetailMapper {
	@Autowired
	protected ProductRepository productRepository;
	
	@Mapping(source = "product.productId", target = "productId")
	public abstract CleanerDetailDto toDto(CleanerDetail cleanerDetail);
	
	@Mapping(target = "product", ignore = true)  
	public abstract CleanerDetail toEntity(CleanerDetailDto cleanerDetailDto);
	
	public abstract List<CleanerDetailDto> toDtoList(List<CleanerDetail> cleanerDetails);
	public abstract List<CleanerDetail> toEntityList(List<CleanerDetailDto> cleanerDetailDtos);
	
	public Page<CleanerDetailDto> toPage(Page<CleanerDetail> cleanerDetails){
    	return cleanerDetails.map(this::toDto);
    }
	
	@AfterMapping
	protected void afterToEntity(CleanerDetailDto cleanerDetailDto, @MappingTarget CleanerDetail cleanerDetail) {
		Product product = productRepository.findById(cleanerDetailDto.getProductId())
				.orElseThrow(() -> new EntityExistsException(cleanerDetailDto.getProductId() + " not found"));
		cleanerDetail.setProduct(product);
	}
}
