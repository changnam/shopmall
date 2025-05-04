package com.honsoft.shopmall.service;

import com.honsoft.shopmall.entity.Cart;

public interface CartService {
	
	Cart create(Cart cart);
	
	Cart read(String cartId);
	
	 void update(String cartId, Cart cart);  
	 void delete(String cartId);
	 Cart validateCart(String cartId);
}
