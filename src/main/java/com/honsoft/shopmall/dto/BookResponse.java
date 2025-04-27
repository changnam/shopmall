package com.honsoft.shopmall.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookResponse(long id, @NotNull @NotBlank String bookId, String name, BigDecimal unitPrice, String author,
		String description, String publisher, String category, long unitsInStock, String releaseDate, String condition, String fileName) {

}
