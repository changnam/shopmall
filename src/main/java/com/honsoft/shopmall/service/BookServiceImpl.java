package com.honsoft.shopmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.repository.BookRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	
	private final Validator validator;
	
	@Value("${file.uploadDir}")
	String fileDir;

	public BookServiceImpl(BookRepository bookRepository,Validator validator) {
		this.bookRepository = bookRepository;
		this.validator = validator;
	}

	@Override
	public List<BookResponse> getAllBookList() {
		// TODO Auto-generated method stub
		List<Book> list = bookRepository.getAllBookList();
		return list.stream().map(Book::toBookResponse).toList();
	}

	@Override
	public BookResponse getBookById(String bookId) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> {
			// IllegalArgumentException 예외 처리
			throw new IllegalArgumentException("해당하는 아이디가 없습니다 bookId : " + bookId);
		});

		return book.toBookResponse();
	}

	@Override
	public List<BookResponse> getBooksByCategory(String category) {
		// TODO Auto-generated method stub
		List<Book> list = bookRepository.getBooksByCategory(category);
		return list.stream().map(Book::toBookResponse).toList();
	}

	@Override
	@Transactional
	public BookResponse updateBookById(BookRequest bookRequest) {
		Book book = bookRepository.findByBookId(bookRequest.getBookId()).orElseThrow(() -> {
			// IllegalArgumentException 예외 처리
			throw new IllegalArgumentException("해당하는 아이디가 없습니다 bookId : " + bookRequest.getBookId());
		});

		book.setAuthor(bookRequest.getAuthor());
		book.setBookId(bookRequest.getBookId());
		book.setCategory(bookRequest.getCategory());
		book.setCondition(bookRequest.getCondition());
		book.setDescription(bookRequest.getDescription());
		book.setName(bookRequest.getName());
		book.setPublisher(bookRequest.getPublisher());
		book.setReleaseDate(bookRequest.getReleaseDate());
		book.setUnitPrice(bookRequest.getUnitPrice());
		book.setUnitsInStock(bookRequest.getUnitsInStock());

		bookRepository.save(book);
		return book.toBookResponse();
	}

	@Override
	@Transactional
	public BookResponse insertBook(BookRequest bookRequest) throws IllegalStateException, IOException {
		
		Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		Optional<Book> checkBook = bookRepository.findByBookId(bookRequest.getBookId());

		if (checkBook.isEmpty()) {
			Book targetBook = Book.from(bookRequest);
			if (bookRequest.getBookImage() != null) {
				String fileName = bookRequest.getBookImage().getOriginalFilename();
				File savedFile = new File(fileDir, fileName);
				bookRequest.getBookImage().transferTo(savedFile);
				targetBook.setFileName(fileName);
			}
			Book savedBook = bookRepository.save(targetBook);
			return savedBook.toBookResponse();
		} else {
			throw new RuntimeException("bookId " + bookRequest.getBookId() + " already exists");
		}
	}

}
