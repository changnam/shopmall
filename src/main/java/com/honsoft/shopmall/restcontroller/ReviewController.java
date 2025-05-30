package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.ReviewDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.ExceptionMessageService;
import com.honsoft.shopmall.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final ExceptionMessageService exceptionMessageService;
	
	public ReviewController(ReviewService reviewService,ExceptionMessageService exceptionMessageService) {
		this.reviewService = reviewService;
		this.exceptionMessageService = exceptionMessageService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<Object> addReviewToProductWithId(@PathVariable("id") Long productId,
			@RequestBody ReviewDto reviewDto) {
		ReviewDto created = reviewService.addReviewToProduct(productId, reviewDto);
		return ResponseHandler.responseBuilder("review created", HttpStatus.OK, created);
	}
	
	@PostMapping
	public ResponseEntity<Object> addReviewToProduct(@RequestBody ReviewDto reviewDto) {
		ReviewDto created = reviewService.addReviewToProduct(reviewDto.getProductId(), reviewDto);
		return ResponseHandler.responseBuilder("review created", HttpStatus.OK, created);
	}

	@GetMapping
	public ResponseEntity<Object> getAllReviews() {
		List<ReviewDto> list = reviewService.getAllReviews();
		return ResponseHandler.responseBuilder("review get success", HttpStatus.OK, list);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Object> getPageReviews( @PageableDefault(page = 0, size = 10, sort = "reviewId", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<ReviewDto> page = reviewService.getPageReviews(pageable);
		return ResponseHandler.responseBuilder("review get success", HttpStatus.OK, page);
	}

	@GetMapping("/byproduct/{id}")
	public ResponseEntity<Object> getReviewsByProductId(@PathVariable("id") Long productId) {
		List<ReviewDto> reviewDtos = reviewService.getReviewsByProductId(productId);
		return ResponseHandler.responseBuilder("review get success", HttpStatus.OK, reviewDtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getReviewsById(@PathVariable("id") Long reviewId) {
		ReviewDto reviewDto = reviewService.getReviewById(reviewId);
		return ResponseHandler.responseBuilder("review get success", HttpStatus.OK, reviewDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateReviewWithId(@PathVariable("id") Long reviewId,@RequestBody ReviewDto reviewDto){
		ReviewDto updated = reviewService.updateReview(reviewId, reviewDto);
		return ResponseHandler.responseBuilder("update success", HttpStatus.OK, updated);
	}
	
	@PutMapping
	public ResponseEntity<Object> updateReview(@RequestBody ReviewDto reviewDto){
		ReviewDto updated = reviewService.updateReview(reviewDto.getProductId(), reviewDto);
		return ResponseHandler.responseBuilder("update success", HttpStatus.OK, updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteReview(@PathVariable("id") Long reviewId){
		reviewService.deleteReview(reviewId);
		return ResponseHandler.responseBuilder("delete success", HttpStatus.OK, reviewId);
	}
}
