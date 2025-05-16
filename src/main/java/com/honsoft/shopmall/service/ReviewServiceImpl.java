package com.honsoft.shopmall.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.ReviewDto;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.entity.Review;
import com.honsoft.shopmall.mapper.ReviewMapper;
import com.honsoft.shopmall.repository.ProductRepository;
import com.honsoft.shopmall.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final ReviewMapper reviewMapper;

	public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository,
			ReviewMapper reviewMapper) {
		this.reviewRepository = reviewRepository;
		this.productRepository = productRepository;
		this.reviewMapper = reviewMapper;
	}

	@Transactional
	@Override
	public ReviewDto addReviewToProduct(Long productId, ReviewDto dto) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Review review = reviewMapper.toEntity(dto);
		review.setProduct(product);

		Review saved = reviewRepository.save(review);

		return reviewMapper.toDto(saved);
	}

	@Override
	public List<ReviewDto> getAllReviews() {
		List<Review> reviews = reviewRepository.findAll();
		List<ReviewDto> dtos = reviewMapper.toDtoList(reviews);
		return dtos;
	}

	@Override
	public List<ReviewDto> getReviewsByProductId(Long productId) {
		List<Review> reviews = reviewRepository.findByProductProductId(productId); // product 의 productId 로 조회
		List<ReviewDto> dtos = reviewMapper.toDtoList(reviews);
		return dtos;
	}

	@Override
	public Page<ReviewDto> getPageReviews(Pageable pageable) {
		Page<Review> reviews = reviewRepository.findAll(pageable);
		Page<ReviewDto> dtos = reviewMapper.toPage(reviews);
		return dtos;
	}

	@Transactional
	@Override
	public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
		Review existing = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new EntityNotFoundException(reviewId + " not found"));
		Review updating = reviewMapper.toEntity(reviewDto);
		existing.setContent(updating.getContent());
		existing.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		existing.setLastModifiedDate(LocalDateTime.now());
		existing.setReviewer(updating.getReviewer());
		existing.setScore(updating.getScore());
		existing.setUpdatedAt(Instant.now());
		Review updated = reviewRepository.save(existing);
		ReviewDto updatedDto = reviewMapper.toDto(updated);
		return updatedDto;
	}

	@Transactional
	@Override
	public void deleteReview(Long reviewId) {
		Review existing = reviewRepository.findById(reviewId).orElseThrow(()->new EntityNotFoundException(reviewId+" not found"));
		reviewRepository.delete(existing);
	}

}
