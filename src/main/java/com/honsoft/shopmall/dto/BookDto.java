package com.honsoft.shopmall.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String bookId;
    private String title;
    private BigDecimal unitPrice;
	private String name; // 도서명
	private String description; // 설명
	private String publisher; // 출판사
	private String category; // 분류	
	private long unitsInStock; // 재고수	
	private String releaseDate; // 출판일	
	private String condition; // 상태 : 신규도서/E-Book/중고도서
	private String fileName; //도서 이미지 파일	
	private String isbn;
	private MultipartFile bookImage;
    private Long authorId; // nested mapping
    private String authorName; // nested mapping
    
}
