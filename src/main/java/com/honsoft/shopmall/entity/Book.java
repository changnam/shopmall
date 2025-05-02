package com.honsoft.shopmall.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
@ToString
public class Book extends Product {
	
	@Column(unique = true, nullable = false)
	private String bookId;
    private String author;
    private String publisher;
    private LocalDate releaseDate;
    private String description;
    private String fileName;
    
    public Book(String bookId,String name,BigDecimal unitPrice,String author, String description, String publisher, String category,
    		Integer unitsInStock,LocalDate releaseDate,String productCondition, String fileName) {
    	this.bookId = bookId;
		setName(name);
		setUnitPrice(unitPrice);
		this.author = author;
		this.description = description;
		this.publisher = publisher;
		setCategory(category);
		setUnitsInStock(unitsInStock);
		this.releaseDate = releaseDate;
		setProductCondition(productCondition);
		this.fileName = fileName;
//		this.bookImage = bookImage;
    }
    public static Book from(BookRequest request) {
	    return new Book(
	        request.bookId(),
	        request.name(),
	        request.unitPrice(),
	        request.author(),
	        request.description(),
	        request.publisher(),
	        request.category(),
	        request.unitsInStock(),
	        request.releaseDate(),
	        request.productCondition(),
	        request.fileName()
	    );
	}
    
    public BookResponse toBookResponse() {
	    return new BookResponse(
	    	this.getId(),
	        this.bookId,
	        this.getName(),
	        this.getUnitPrice(),
	        this.author,
	        this.description,
	        this.publisher,
	        this.getCategory(),
	        this.getUnitsInStock(),
	        this.releaseDate,
	        this.getProductCondition(),
	        this.fileName
	    );
	 }
}
