package com.honsoft.shopmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {
	private BookRepository bookRepository;
	@Value("${file.uploadDir}")
	String fileDir;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
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
		Book book = bookRepository.findByBookId(bookRequest.bookId()).orElseThrow(() -> {
			// IllegalArgumentException 예외 처리
			throw new IllegalArgumentException("해당하는 아이디가 없습니다 bookId : " + bookRequest.bookId());
		});

		book.setAuthor(bookRequest.author());
		book.setBookId(bookRequest.bookId());
		book.setCategory(bookRequest.category());
		book.setCondition(bookRequest.condition());
		book.setDescription(bookRequest.description());
		book.setName(bookRequest.name());
		book.setPublisher(bookRequest.publisher());
		book.setReleaseDate(bookRequest.releaseDate());
		book.setUnitPrice(bookRequest.unitPrice());
		book.setUnitsInStock(bookRequest.unitsInStock());

		bookRepository.save(book);
		return book.toBookResponse();
	}

	@Override
	@Transactional
	public BookResponse insertBook(BookRequest bookRequest) throws IllegalStateException, IOException {
		Optional<Book> checkBook = bookRepository.findByBookId(bookRequest.bookId());

		if (checkBook.isEmpty()) {
			Book targetBook = Book.from(bookRequest);
			if (bookRequest.bookImage() != null) {
				String fileName = bookRequest.bookImage().getOriginalFilename();
				File savedFile = new File(fileDir, fileName);
				bookRequest.bookImage().transferTo(savedFile);
				targetBook.setFileName(fileName);
			}
			Book savedBook = bookRepository.save(targetBook);
			return savedBook.toBookResponse();
		} else {
			throw new RuntimeException("bookId " + bookRequest.bookId() + " already exists");
		}
	}

}
