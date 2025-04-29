package com.honsoft.shopmall.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.honsoft.shopmall.validation.BookId;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookRequest{
		@Pattern(regexp="ISBN[1-9]+")
		@BookId
		private String bookId;
		
		@Size(min=4, max=50)
		private String name;
		
		@Min(value=0)
		@Digits(integer=8,fraction=2)
		@NotNull
		private BigDecimal unitPrice;
		
		private String author;
		private String description;
		private String publisher;
		private String category;
		private Long unitsInStock;
		private String releaseDate;
		private String condition;
		private String fileName;
		private MultipartFile bookImage;
		
		public BookRequest() {
			this(null,null,null,null,null,null,null,null,null,null,null,null);
		}
		public BookRequest(
				String bookId,
				String name, BigDecimal unitPrice, String author, String description, String publisher, String category,
				Long unitsInStock, String releaseDate, String condition, String fileName, MultipartFile bookImage) {
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
			this.bookImage = bookImage;
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
		public void setUnitsInStock(Long unitsInStock) {
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
		public MultipartFile getBookImage() {
			return bookImage;
		}
		public void setBookImage(MultipartFile bookImage) {
			this.bookImage = bookImage;
		}
		
		

}
