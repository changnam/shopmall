package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ReviewDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	
//	@Mapping(source = "product.productId", target = "productId")
	ReviewDto toDto(Review review);
	
	Review toEntity(ReviewDto reviewDto);
	
	List<ReviewDto> toDtoList(List<Review> reviews);
	List<Review> toEntityList(List<ReviewDto> reviewDtos);
	
	default Page<ReviewDto> toPage(Page<Review> reviews) {
		return reviews.map(this::toDto);
	}
}
