package com.honsoft.shopmall.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.BookIdException;
import com.honsoft.shopmall.exception.CategoryException;
import com.honsoft.shopmall.service.AuthorService;
import com.honsoft.shopmall.service.BookService;
import com.honsoft.shopmall.validator.BookValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;

	@Value("${file.uploadDir}")
	String fileDir;

	// @Autowired
	// private UnitsInStockValidator unitsInStockValidator;

	@Autowired
	private BookValidator bookValidator; // BookValidator 인스턴스 선언
	
	@Autowired
	private AuthorService authorService;

	@GetMapping
	public String requestBookList(Model model) {

		List<BookDto> list = bookService.getAllBooks();
		model.addAttribute("bookList", list);
		return "books/bookList";
	}
	/*
	 * @GetMapping("/all") public String requestAllBooks(Model model) { List<Book>
	 * list = bookService.getAllBookList(); model.addAttribute("bookList", list);
	 * return "books"; }
	 */

	@GetMapping("/all")
	public ModelAndView requestAllBooks() {
		ModelAndView modelAndView = new ModelAndView();
		List<Book> list = bookService.getAllBookList();
		modelAndView.addObject("bookList", list);
		modelAndView.setViewName("books");
		return modelAndView;
	}

	@GetMapping("/{category}")
	public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
		List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);

		if (booksByCategory == null || booksByCategory.isEmpty()) {
			throw new CategoryException();
			// throw new IllegalArgumentException("도서ID가 인 해당 도서를 찾을 수 없습니다.");

		}

		model.addAttribute("bookList", booksByCategory);
		return "books/bookList";
	}

	@GetMapping("/filter/{bookFilter}")
	public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter,
			Model model) {
		Set<Book> booksByFiter = bookService.getBookListByFilter(bookFilter);
		model.addAttribute("bookList", booksByFiter);
		return "books/bookList";
	}

	@GetMapping("/book")
	public String requestBookById(@RequestParam("id") String bookId, Model model) {
		Book bookById = bookService.getBookById(bookId);
		model.addAttribute("book", bookById);
		return "books/bookDetail";
	}

	@GetMapping("/add")
	public String requestAddBookForm(Model model) {
		model.addAttribute("book", new BookDto()); // 유효검사기 추가
		List<AuthorDto> authors = authorService.getAllAuthors();
		model.addAttribute("authors",authors);
		return "books/bookAdd";
	}

	@PostMapping("/add")
	public String submitAddNewBook(@Valid @ModelAttribute BookDto bookDto, BindingResult bindingResult) throws BindException {

		if (bindingResult.hasErrors())
			return "books/bookAdd";

		MultipartFile bookImage = bookDto.getBookImage();

		String saveName = bookImage.getOriginalFilename();
		File saveFile = new File(fileDir, saveName);

		if (bookImage != null && !bookImage.isEmpty()) {
			try {
				bookImage.transferTo(saveFile);
			} catch (Exception e) {
				throw new RuntimeException("도서 이미지 업로드가 실패하였습니다", e);
			}
		}
		bookDto.setFileName(saveName);

//		bookService.setNewBook(bookDto);
		bookService.createBook(bookDto);

		return "redirect:/books";
	}

	@GetMapping("/download")
	public void downloadBookImage(@RequestParam("file") String paramKey, HttpServletResponse response)
			throws IOException {

		if (paramKey == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		File imageFile = new File(fileDir + paramKey);

		if (imageFile.exists() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/download");
		response.setContentLength((int) imageFile.length());
		response.setHeader("Content-disposition", "attachment;filename=\"" + paramKey + "\"");
		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(imageFile);
		FileCopyUtils.copy(fis, os);
		fis.close();
		os.close();

	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("addTitle", "신규 도서 등록");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (target != null && Book.class.isAssignableFrom(target.getClass())) {
			binder.addValidators(bookValidator);
			binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher", "category",
					"unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
		}
		// binder.setValidator(unitsInStockValidator);
//		binder.setValidator(bookValidator); 
//		 binder.setAllowedFields("bookId","name","unitPrice","author","description","publisher","category",
//	                            "unitsInStock","totalPages", "releaseDate", "condition", "bookImage");
	}

	@ExceptionHandler(value = { BookIdException.class })
	public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidBookId", exception.getBookId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("errorBook");
		return mav;
	}

	@GetMapping("/update")
	public String getUpdateBookForm(@ModelAttribute("updateBook") Book book, @RequestParam("id") String bookId,
			Model model) {
		Book bookById = bookService.getBookById(bookId);
		model.addAttribute("book", bookById);
		return "books/bookUpdateForm";
	}

	@PostMapping("/update")
	public String processUpdatewBookForm(@ModelAttribute("updateBook") Book book) {
		MultipartFile bookImage = book.getBookImage();

		// String rootDirectory = fileDir;
		if (bookImage != null && !bookImage.isEmpty()) {
			try {
				String fname = bookImage.getOriginalFilename();
				bookImage.transferTo(new File(fileDir + fname));
				book.setFileName(fname);
			} catch (Exception e) {
				throw new RuntimeException("Book Image saving failed", e);
			}
		}
		bookService.setUpdateBook(book);
		return "redirect:/books";
	}

	@RequestMapping(value = "/delete")
	public String getDeleteBookForm(Model model, @RequestParam("id") String bookId) {
		bookService.setDeleteBook(bookId);
		return "redirect:/books";
	}

}
