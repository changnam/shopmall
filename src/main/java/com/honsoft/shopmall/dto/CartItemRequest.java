package com.honsoft.shopmall.dto;

import java.math.BigDecimal;

import com.honsoft.shopmall.entity.Book;

public record CartItemRequest(
		Book book,Integer quantity,BigDecimal totalPrice) {

}
