package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.ComputerDetailDto;
import com.honsoft.shopmall.entity.ComputerDetail;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.repository.ProductRepository;

import jakarta.persistence.EntityExistsException;

@Mapper(componentModel = "spring")
public abstract class ComputerDetailMapper {
	@Autowired
	protected ProductRepository productRepository;

	@Mapping(source = "product.productId", target = "productId")
	public abstract ComputerDetailDto toDto(ComputerDetail computerDetail);

	@Mapping(target = "product", ignore = true)
	public abstract ComputerDetail toEntity(ComputerDetailDto computerDetailDto, @Context MappingContext context);

	public abstract List<ComputerDetailDto> toDtoList(List<ComputerDetail> computerDetails);
//	public abstract List<ComputerDetail> toEntityList(List<ComputerDetailDto> computerDetailDtos);

	public Page<ComputerDetailDto> toPage(Page<ComputerDetail> computerDetails) {
		return computerDetails.map(this::toDto);
	}

	@AfterMapping
	protected void afterToEntity(ComputerDetailDto computerDetailDto, @Context MappingContext context,
			@MappingTarget ComputerDetail computerDetail) {

		if (context.isUpdate()) {
			Product product = productRepository.findById(computerDetailDto.getProductId())
					.orElseThrow(() -> new EntityExistsException(computerDetailDto.getProductId() + " not found"));
			computerDetail.setProduct(product);
		}
	}
}
