package com.honsoft.shopmall.service;

import com.honsoft.shopmall.entity.Order;

public interface OrderService {
	void confirmOrder(String bookId, long quantity);
	void saveOrder(Order order);
}
