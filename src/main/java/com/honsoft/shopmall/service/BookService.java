package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.BindException;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Book;

public interface BookService {

	List<Book> getAllBookList();
	List<Book> getBookListByCategory(String category);
	Set<Book> getBookListByFilter(Map<String, List<String>> filter);
	Book getBookById(String bookId);
	void setNewBook(Book book);

	void setUpdateBook(Book book);
	void setDeleteBook(String bookID);
	
	BookDto createBook(BookDto bookDto) throws BindException;
	BookDto getBook(String bookId);
	List<BookDto> getAllBooks();
	List<BookDto> saveAllBooks(List<BookDto> books);
}
