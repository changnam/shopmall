package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.service.AuthorService;
import com.honsoft.shopmall.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private final BookService bookService;
	private final AuthorService authorService;
	
	public BookController( @Qualifier("bookServiceImpl") BookService bookService,AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}
	
	@GetMapping
	public ResponseEntity<List<BookDto>> getAllBooks(){
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.CREATED);
	}

}
