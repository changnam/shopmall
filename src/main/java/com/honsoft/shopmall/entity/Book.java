package com.honsoft.shopmall.entity;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String bookId;
	private String name;
	private BigDecimal unitPrice;
	private String author;
	private String description;
	private String publisher;
	private String category;
	private long unitsInStock;
	private String releaseDate;
	private String condition;
	private String fileName;
//	private MultipartFile bookImage;

	public Book() {}
	public Book(String bookId, String name, BigDecimal unitPrice, String author, String description,
			String publisher, String category, long unitsInStock, String releaseDate, String condition, String fileName) {
		this.bookId = bookId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.author = author;
		this.description = description;
		this.publisher = publisher;
		this.category = category;
		this.unitsInStock = unitsInStock;
		this.releaseDate = releaseDate;
		this.condition = condition;
		this.fileName = fileName;
//		this.bookImage = bookImage;
	}


	public static Book from(BookRequest request) {
	    return new Book(
	        request.getBookId(),
	        request.getName(),
	        request.getUnitPrice(),
	        request.getAuthor(),
	        request.getDescription(),
	        request.getPublisher(),
	        request.getCategory(),
	        request.getUnitsInStock(),
	        request.getReleaseDate(),
	        request.getCondition(),
	        request.getFileName()
	    );
	}

	public BookResponse toBookResponse() {
	    return new BookResponse(
	    	this.id,
	        this.bookId,
	        this.name,
	        this.unitPrice,
	        this.author,
	        this.description,
	        this.publisher,
	        this.category,
	        this.unitsInStock,
	        this.releaseDate,
	        this.condition,
	        this.fileName
	    );
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(long unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
