package com.honsoft.shopmall.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.BookService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;
	private final MessageSource messageSource;
//	private final ModelMapper modelMapper;
	@Value("${file.uploadDir}")
	String fileDir;

	private static Logger logger = LoggerFactory.getLogger(BookController.class);

	public BookController(BookService bookService,MessageSource messageSource) {
		this.bookService = bookService;
		this.messageSource = messageSource;
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

	@GetMapping("/json")
	public @ResponseBody ResponseEntity<Object> requestBookListJson(Locale locale) {
		return ResponseHandler.responseBuilder(messageSource.getMessage("success.message",null,locale),HttpStatus.OK,bookService.getAllBookList());
		
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
	public String requestAddBookForm(Model m) {
		m.addAttribute("book",new BookRequest());
		return "books/addBook";
	}

	@PostMapping("/add")
	public String requestAddBook(@Validated @ModelAttribute("book") BookRequest bookRequest, BindingResult bindingResult) throws Exception {
		// Book bookTemp = modelMapper.map(addBookRequest, Book.class);
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
		// bookService.insertBook(modelMapper.map(addBookRequest, Book.class));
		if (bindingResult.hasErrors()) {
			return "books/addBook";
        }
		
		bookService.insertBook(bookRequest);
		return "redirect:/books";
	}

	@GetMapping("/upload")
	public String requestUploadForm() {
		return "uploadForm";
	}

	@PostMapping("/upload")
	public String processUploadFrom(@RequestParam("name") String name, @RequestParam("file") MultipartFile file,
			Model m) throws IllegalStateException, IOException {
		logger.info("name: " + name + ", filename: " + file.getOriginalFilename());
		m.addAttribute("fileName", file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		File savedFile = new File("c:\\upload\\files\\" + name + "_" + fileName);
//		try {
		file.transferTo(savedFile);
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
		return "uploadResult";
	}

	@GetMapping("/download")
	public void downloadBookImage(@RequestParam("file") String paramKey, HttpServletResponse resp) throws IOException {
		File imageFile = new File(fileDir + paramKey);
		resp.setContentType("application/download");
		resp.setContentLength((int) imageFile.length());
		resp.setHeader("Content-disposition", "attachment;filename=\"" + paramKey + "\"");
		OutputStream os = resp.getOutputStream();
		FileInputStream fis = new FileInputStream(imageFile);
		FileCopyUtils.copy(fis, os);
		fis.close();
		os.close();
	}

	@GetMapping("/error")
	public String requestBooksByError() {
		logger.info("requestBooksByError started.");
		int divideByZero = 40 / 0;
		throw new ErrorResponseException(HttpStatusCode.valueOf(900));
	}

}
