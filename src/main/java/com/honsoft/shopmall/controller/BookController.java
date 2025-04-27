package com.honsoft.shopmall.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;
//	private final ModelMapper modelMapper;
	@Value("${file.uploadDir}")
	String fileDir;
	
	private static Logger logger = LoggerFactory.getLogger(BookController.class);

	public BookController(BookService bookService) {
		this.bookService = bookService;
//		this.modelMapper = modelMapper;
//		configureModelMapper();
	}
//	    private void configureModelMapper() {
//	        modelMapper.createTypeMap(AddBookRequest.class, Book.class)
//	            .addMappings(mapper -> mapper.skip(Book::setId));
//	    }
	    
	   
	@GetMapping
	public String requestBookList(Model m) {
		logger.info("requestBookList started.");
		List<BookResponse> list = bookService.getAllBookList();
		
		m.addAttribute("bookList", list);
		return "books/bookList";
	}

	@GetMapping("/book")
	public String requestBookById(@RequestParam("id") String bookId, Model m) {
		BookResponse bookById = bookService.getBookById(bookId);
		m.addAttribute("book", bookById);
		return "books/bookDetail";
	}

	@GetMapping("/{category}")
	public String requestBooksByCategory(@PathVariable("category") String category, Model m) {
		logger.info("requestBooksByCategory started.");
		List<BookResponse> list = bookService.getBooksByCategory(category);
		m.addAttribute("bookList", list);
		return "books/bookList";
	}
	
	@GetMapping("/add")
	public String requestAddBookForm() {
		return "books/addBook";
	}
	
	@PostMapping("/add")
	public String requestAddBook(@Validated BookRequest bookRequest) throws IllegalStateException, IOException {
		//Book bookTemp = modelMapper.map(addBookRequest, Book.class);
		/*
		 * Book bookTemp = new Book(); bookTemp.setAuthor(addBookRequest.author());
		 * bookTemp.setBookId(addBookRequest.bookId());
		 * bookTemp.setCategory(addBookRequest.category());
		 * bookTemp.setCondition(addBookRequest.condition());
		 * bookTemp.setDescription(addBookRequest.description());
		 * bookTemp.setName(addBookRequest.name());
		 * bookTemp.setPublisher(addBookRequest.publisher());
		 * bookTemp.setReleaseDate(addBookRequest.releaseDate());
		 * bookTemp.setUnitPrice(addBookRequest.unitPrice());
		 * bookTemp.setUnitsInStock(addBookRequest.unitsInStock());
		 */
		//bookService.insertBook(modelMapper.map(addBookRequest, Book.class));
		bookService.insertBook(bookRequest);
		return "redirect:/books";
	}

	
	@GetMapping("/upload")
	public String requestUploadForm() {
		return "uploadForm";
	}
	
	@PostMapping("/upload")
	public String processUploadFrom(@RequestParam("name") String name, @RequestParam("file") MultipartFile file, Model m) throws IllegalStateException, IOException {
		logger.info("name: "+name+", filename: "+file.getOriginalFilename());
		m.addAttribute("fileName",file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		File savedFile = new File("c:\\upload\\files\\"+name+"_"+fileName);
//		try {
			file.transferTo(savedFile);
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
		return "uploadResult";
	}
	
	@GetMapping("/error")
	public String requestBooksByError() {
		logger.info("requestBooksByError started.");
		int divideByZero = 40 / 0;
		throw new ErrorResponseException(HttpStatusCode.valueOf(900));
	}
	
}
