package com.honsoft.shopmall.service;

import java.util.Optional;

import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;

public interface ProductService {
	Optional<Product> getProductById(Long id);
	Book saveBook(Book book);
	Computer saveComputer(Computer computer);
	void deleteProductById(Long id);
}
