package com.honsoft.shopmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.honsoft.shopmall.validator.BookId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Book  implements Serializable {
	
	private static final long serialVersionUID = -7715651009026349175L;
	@Id
	@BookId
	@Pattern(regexp="ISBN[1-9]+", message="{Pattern.book.bookId}")	
	@Column(name = "b_bookId")
	private String bookId; //도서ID
	
	@Size(min=4, max=50, message="{Size.book.name}")
	@Column(name = "b_name")
	private String name; // 도서명
	
	@Min(value=0, message="{Min.book.unitPrice}")	
	@Digits(integer=8, fraction=2, message="{Digits.book.unitPrice}")	
	@NotNull(message="{NotNull.book.unitPrice}")	
	@Column(name = "b_unitPrice")
	private BigDecimal unitPrice; // 가격	
	
	@Column(name = "b_author")
	private String author; // 저자	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="author_id")
	@JsonBackReference
	private Author eauthor;

	@Column(name = "b_description")
	private String description; // 설명
	
	@Column(name = "b_publisher")
	private String publisher; // 출판사	
	
	@Column(name = "b_category")
	private String category; // 분류	
	
	@Column(name = "b_unitsInStock")
	private long unitsInStock; // 재고수	
	
	@Column(name = "b_releaseDate")
	private String releaseDate; // 출판일	
	
	@Column(name = "b_condition")
	private String condition; // 상태 : 신규도서/E-Book/중고도서	

	@Column(name = "b_fileName")
	private String fileName; //도서 이미지 파일	
	
	@Column(name = "b_isbn")
	private String isbn;
	
	@Column(name = "b_title")
	private String title;
	
	@Transient
	private MultipartFile bookImage;  //도서 이미지
/*
	public Book() {
		super();
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

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
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
*/
}
