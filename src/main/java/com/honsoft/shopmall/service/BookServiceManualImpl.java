package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.repository.BookRepositoryManual;


@Service("bookServiceManualImpl")
public class BookServiceManualImpl implements BookService {

	@Autowired
	private BookRepositoryManual bookRepository;

	public List<Book> getAllBookList() {
		return bookRepository.getAllBookList();
	}
	
	public List<Book> getBookListByCategory(String category) { 
	      List<Book> booksByCategory = bookRepository.getBookListByCategory(category); 
	      return booksByCategory;  
	}	
	
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
	      Set<Book> booksByFilter = bookRepository.getBookListByFilter(filter); 
	      return booksByFilter;
	}
	
	public Book getBookById(String bookId) {
	     Book bookById = bookRepository.getBookById(bookId);
	     return bookById;
	}	
	 public void setNewBook(Book book) { 
		    bookRepository.setNewBook(book); 
		 }

	 public void setUpdateBook(Book book) {
	      bookRepository.setUpdateBook(book);
	 }
	 
	 public void setDeleteBook(String bookID) {
	      bookRepository.setDeleteBook(bookID);
	   }

	@Override
	public BookDto createBook(BookDto bookDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDto getBook(String bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDto> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDto> saveAllBooks(List<BookDto> books) {
		// TODO Auto-generated method stub
		return null;
	}
}
