package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
	private Long reviewId;

	private String reviewer;
	private String content;
	private Integer score;
	
	private Long productId;
}
