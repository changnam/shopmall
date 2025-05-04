package com.honsoft.shopmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.ToString;
@Data 
@ToString
//@Entity
public class Cart implements Serializable {	
	
	private static final long serialVersionUID = 2155125089108199199L;
	
	
   	private String cartId;
   
	
	private Map<String,CartItem> cartItems;
    
	private BigDecimal grandTotal;
	
	
	public Cart() {
		cartItems = new HashMap<String, CartItem>();
		grandTotal = new BigDecimal(0);
	}

	public Cart(String cartId) {
		this();
		this.cartId = cartId;
	}
/*
	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}
	*/
	
	public void addCartItem(CartItem item) {
	     String bookId = item.getBook().getBookId(); 

		 if(cartItems.containsKey(bookId)) {   
			 CartItem cartItem = cartItems.get(bookId);
			 cartItem.setQuantity(cartItem.getQuantity()+item.getQuantity()); 
			 cartItems.put(bookId, cartItem); 
		 } else {
			 cartItems.put(bookId, item);  
		 }
		 updateGrandTotal(); 
	}
	
	public void removeCartItem(CartItem item) {
		String bookId = item.getBook().getBookId();
		cartItems.remove(bookId);
		updateGrandTotal();
	}
	
	public void updateGrandTotal() {
		grandTotal= new BigDecimal(0);
		for(CartItem item : cartItems.values()){
			grandTotal = grandTotal.add(item.getTotalPrice());
		}
	}
}
