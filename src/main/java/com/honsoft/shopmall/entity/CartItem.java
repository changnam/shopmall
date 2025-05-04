package com.honsoft.shopmall.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
@Data
@ToString

public class CartItem  implements Serializable {
    private static final long serialVersionUID = 3636831123198280235L;
    
    @Id
   	private Long id;   

	private Book book;
	
	private int quantity;
	
	private BigDecimal  totalPrice;
	
	
	public CartItem(Book book) {
		super();
		this.book = book;
		this.quantity = 1;
		this.totalPrice = book.getUnitPrice();
	}
	/*
	public CartItem() {
	
	}

		public Book getBook() {
		return book;
	}
*/	
	public void setBook(Book book) {
		this.book = book;
		this.updateTotalPrice();
	}
	
/*	public int getQuantity() {
		return quantity;
	}
*/
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.updateTotalPrice();
	}
	/*	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
*/
	public void updateTotalPrice() {		
		totalPrice =  this.book.getUnitPrice().multiply(new BigDecimal(this.quantity));
	}	
	
}
