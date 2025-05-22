package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.NotFoundException;
import com.honsoft.shopmall.mapper.BookMapper;
import com.honsoft.shopmall.repository.AuthorRepository;
import com.honsoft.shopmall.repository.BookRepository;

@Service("bookServiceImpl")
@Primary
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final BookMapper bookMapper;
	private final Validator validator;

	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper,
			@Qualifier("validator") Validator validator) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.bookMapper = bookMapper;
		this.validator = validator;
	}

	@Override
	public List<Book> getAllBookList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookListByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getBookById(String bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNewBook(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpdateBook(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDeleteBook(String bookID) {
		// TODO Auto-generated method stub

	}

	@Transactional
	public BookDto createBook(BookDto dto) throws BindException {
		Author author = authorRepository.findById(dto.getAuthorId())
				.orElseThrow(() -> new NotFoundException("Author not found"));

//	        Book existing = bookRepository.findById(dto.getBookId()).ifPresent(() -> {throw new RuntimeException(dto.getBookId() + " already exists");});
		bookRepository.findById(dto.getBookId()).ifPresent(b -> {
			throw new RuntimeException(dto.getBookId() + " already exists");
		});

		Book book = bookMapper.toEntity(dto);
		
		 BeanPropertyBindingResult errors = new BeanPropertyBindingResult(book, "book");
//		    bookValidator.validate(book, errors); // Custom validation
		    validator.validate(book, errors);     // Optional: JSR-380 programmatic validation

		    if (errors.hasErrors()) {
		        throw new BindException(errors); // Or handle manually
		    }
		    
//		validator.validate(book, Errors errors);
		Book saved = bookRepository.save(book);
		BookDto savedDto = bookMapper.toDto(saved);
		return savedDto;
	}

	public BookDto getBook(String bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
		return bookMapper.toDto(book);
	}

	public BookDto updateBook(String bookId, BookDto dto) {
		Book existing = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

//	        Author author = authorRepository.findById(dto.getAuthorId())
//	                .orElseThrow(() -> new NotFoundException("Author not found"));

//	        existing.setTitle(dto.getTitle());
//	        existing.setEauthor(author);

//	        return bookRepository.save(existing).toDto();
		return null;
	}

	public void deleteBook(String bookId) {
		bookRepository.deleteById(bookId);
	}

	public Page<BookDto> getAllBooks(Pageable pageable) {
//	        return bookRepository.findAll(pageable).map(book -> book.toDto());
		return null;
	}

	@Override
	public List<BookDto> getAllBooks() {
//			return bookRepository.findAll().stream().map(book -> book.toDto()).collect(Collectors.toList());
		return bookMapper.toDtoList(bookRepository.findAll());
	}

	@Override
	public List<BookDto> saveAllBooks(List<BookDto> books) {
		// TODO Auto-generated method stub
		return null;
	}
}
