package com.honsoft.shopmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.entity.Order;
import com.honsoft.shopmall.repository.BookRepositoryManual;
import com.honsoft.shopmall.repository.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService{
	
	
	@Autowired
	private BookRepositoryManual bookRepository;
		
	@Autowired
	private OrderRepository orderRepository;
		
	
	public void confirmOrder(String bookId, long quantity) {
	   Book bookById = bookRepository.getBookById(bookId);
	   if(bookById.getUnitsInStock() < quantity){
	       throw new IllegalArgumentException("품절입니다. 사용가능한 제고수 :"+ bookById.getUnitsInStock());
	    }
	    bookById.setUnitsInStock(bookById.getUnitsInStock() - quantity);
	}

	public void saveOrder(Order order) {
		orderRepository.saveOrder(order);
	  // Long orderId = orderRepository.saveOrder(order);	 
	  // return orderId;
	}  

}
