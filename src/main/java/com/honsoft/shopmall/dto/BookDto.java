package com.honsoft.shopmall.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String bookId;
    private String title;
    private BigDecimal unitPrice;
    private AuthorDto author; // nested mapping
}
