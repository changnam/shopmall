package com.honsoft.shopmall.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.honsoft.shopmall.entity.Product;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
}
