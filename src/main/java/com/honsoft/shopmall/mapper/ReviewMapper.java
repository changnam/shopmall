package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.ReviewDto;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.entity.Review;
import com.honsoft.shopmall.repository.ProductRepository;

import jakarta.persistence.EntityExistsException;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
	@Autowired
	protected ProductRepository productRepository;

	@Mapping(source = "product.productId", target = "productId")
	public abstract ReviewDto toDto(Review review);

	@Mapping(target = "product", ignore = true)
	public abstract Review toEntity(ReviewDto reviewDto);

	public abstract List<ReviewDto> toDtoList(List<Review> reviews);

	public abstract List<Review> toEntityList(List<ReviewDto> reviewDtos);

	public Page<ReviewDto> toPage(Page<Review> reviews) {
		return reviews.map(this::toDto);
	}

	@AfterMapping
	protected void afterToEntity(ReviewDto reviewDto, @MappingTarget Review review) {
		Product product = productRepository.findById(reviewDto.getProductId())
				.orElseThrow(() -> new EntityExistsException(reviewDto.getProductId() + " not found"));
		review.setProduct(product);
	}
}
