package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.ReviewDto;

public interface ReviewService {
	ReviewDto addReviewToProduct(Long productId, ReviewDto dto);
	List<ReviewDto> getAllReviews();
	Page<ReviewDto> getPageReviews(Pageable pageable);
	List<ReviewDto> getReviewsByProductId(Long productId);
	ReviewDto updateReview(Long reviewId,ReviewDto reviewDto);
	void deleteReview(Long reviewId);
}
