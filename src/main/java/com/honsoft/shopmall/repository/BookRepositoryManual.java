package com.honsoft.shopmall.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.honsoft.shopmall.entity.Book;

public interface BookRepositoryManual {

	List<Book> getAllBookList();
	List<Book> getBookListByCategory(String category); 
	Set<Book> getBookListByFilter(Map<String, List<String>> filter); 
	Book getBookById(String bookId);
	void setNewBook(Book book);
	void setUpdateBook(Book book);	
	void setDeleteBook(String bookID); 
	boolean existsByBookId(String bookId);
}
