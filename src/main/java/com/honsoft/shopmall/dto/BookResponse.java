package com.honsoft.shopmall.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookResponse(Long id, @NotNull @NotBlank String bookId, String name, BigDecimal unitPrice, String author,
		String description, String publisher, String category, Integer unitsInStock, LocalDate releaseDate, String condition, String fileName) {

}
