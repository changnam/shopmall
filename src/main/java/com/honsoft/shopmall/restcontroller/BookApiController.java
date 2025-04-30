package com.honsoft.shopmall.restcontroller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.BookService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookApiController {
	private static Logger logger = LoggerFactory.getLogger(BookApiController.class);
	private final BookService bookService;
	private final MessageSource messageSource;
	
	public BookApiController(BookService bookService,MessageSource messageSource) {
		this.bookService = bookService;
		this.messageSource = messageSource;
	}
	
	@GetMapping
	public ResponseEntity<Object> requestBookList(Locale locale){
		logger.info("requestBookList started.");
		
		//return bookService.getAllBookList();
		return ResponseHandler.responseBuilder(messageSource.getMessage("success.message",null,locale),HttpStatus.OK,bookService.getAllBookList());
	}
	
	
	@GetMapping("/book")
	public ResponseEntity<Object> requestBookById(@RequestParam("id") String bookId){
		logger.info("requestBookById started.");
		
		return ResponseHandler.responseBuilder("success", HttpStatus.OK, bookService.getBookById(bookId));
	}
	
	@PostMapping("/add")
	public ResponseEntity<Object> requestBookAdd(@RequestBody BookRequest bookRequest) throws IllegalStateException, IOException{
		return ResponseHandler.responseBuilder("success", HttpStatus.OK, bookService.insertBook(bookRequest));
	}
	
	@GetMapping("/{category}")
	public ResponseEntity<Object> requestBooksByCategory(@PathVariable("category") String category) {
		logger.info("requestBooksByCategory started.");
		List<BookResponse> list = bookService.getBooksByCategory(category);
		return ResponseHandler.responseBuilder("success", HttpStatus.OK, list);
	}
	
	@GetMapping("/error")
	public ResponseEntity<Object> requestBooksByError() {
		logger.info("requestBooksByError started.");
		int divideByZero = 40 / 0;
		throw new ErrorResponseException(HttpStatusCode.valueOf(900));
	}
	
}
