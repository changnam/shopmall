package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.service.AuthorService;
import com.honsoft.shopmall.service.BookService;

@RestController("restBookController")
@RequestMapping("/api/books")
public class BookController {
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
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
	
	@PostMapping
	public ResponseEntity<List<BookDto>> saveAllBooks(@RequestBody List<BookDto> bookDtos){
		logger.info("bookDtos: {}",bookDtos);
		return new ResponseEntity<>(bookService.saveAllBooks(bookDtos), HttpStatus.CREATED);
	}

}
