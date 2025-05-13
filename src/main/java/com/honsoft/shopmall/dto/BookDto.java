package com.honsoft.shopmall.dto;

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
    private Long authorId;
    private String authorName; // optional read-only field
}
