package com.honsoft.shopmall.service;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.Cart;
import com.honsoft.shopmall.exception.CartException;
import com.honsoft.shopmall.repository.CartRepository;
@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepository;
	
	public CartServiceImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	public Cart create(Cart cart) {
		return cartRepository.create(cart);
	}

	public Cart read(String cartId) {
		return cartRepository.read(cartId);
	}
	
	public void update(String cartId, Cart cart) {
		cartRepository.update(cartId, cart);
	}   
	
	public void delete(String cartId) {
	      cartRepository.delete(cartId);
	}
	
	public Cart validateCart(String cartId) {
	      Cart cart = cartRepository.read(cartId);
	      if(cart==null || cart.getCartItems().size()==0) {
	    	  throw new CartException(cartId);
	      } 
	      return cart;
	   }
}
