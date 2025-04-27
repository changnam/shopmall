package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.BookService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookApiController {
	private static Logger logger = LoggerFactory.getLogger(BookApiController.class);
	private BookService bookService;
	
	public BookApiController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping
	public ResponseEntity<Object> requestBookList(){
		logger.info("requestBookList started.");
		
		//return bookService.getAllBookList();
		return ResponseHandler.responseBuilder("success",HttpStatus.OK,bookService.getAllBookList());
	}
	
	
	@GetMapping("/book")
	public ResponseEntity<Object> requestBookById(@RequestParam("id") String bookId){
		logger.info("requestBookById started.");
		
		return ResponseHandler.responseBuilder("success", HttpStatus.OK, bookService.getBookById(bookId));
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
