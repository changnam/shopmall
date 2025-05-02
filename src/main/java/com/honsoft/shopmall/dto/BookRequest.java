package com.honsoft.shopmall.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.honsoft.shopmall.validation.BookId;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookRequest(
		@Pattern(regexp="ISBN[1-9]+")
		@BookId
		String bookId,
		
		@Size(min=4, max=50)
		String name,
		
		@Min(value=0)
		@Digits(integer=8,fraction=2)
		@NotNull
		BigDecimal unitPrice,
		
		String author,
		String description,
		String publisher,
		String category,
		Integer unitsInStock,
		LocalDate releaseDate,
		String productCondition,
		String fileName,
		MultipartFile bookImage
		) {
	
	public BookRequest() {
		this(null,null,null,null,null,null,null,null,null,null,null,null);
	}

}
