package com.honsoft.shopmall.service;

import java.io.IOException;
import java.util.List;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;

public interface BookService {
	List<BookResponse> getAllBookList();
	BookResponse getBookById(String bookId);
	List<BookResponse> getBooksByCategory(String category);
	BookResponse updateBookById(BookRequest bookRequest);
	BookResponse insertBook(BookRequest bookRequest) throws IllegalStateException, IOException;
}
