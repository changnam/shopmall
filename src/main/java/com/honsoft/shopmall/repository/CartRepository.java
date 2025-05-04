package com.honsoft.shopmall.repository;

import com.honsoft.shopmall.entity.Cart;

public interface CartRepository {

	Cart create(Cart cart);
	
	Cart read(String cartId);
	
	void update(String cartId, Cart cart);   

	void delete(String cartId);
}
