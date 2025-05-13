package com.honsoft.shopmall.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.repository.AuthorRepository;
import com.honsoft.shopmall.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {
	
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	
	public AuthorService(AuthorRepository authorRepository,BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	@Transactional
	public void insertAuthorWithBooks() {
		Author jn = Author.builder().name("Joana Nimar").age(24).genre("History").build();
		Book jn01 = Book.builder().isbn("001-JN").title("A History of ancient prague").bookId("ISBN1111").unitPrice(new BigDecimal(10000)).build();
		Book jn02 = Book.builder().isbn("002-JN").title("People's History").bookId("ISBN2222").unitPrice(new BigDecimal(20000)).build();
		Book jn03 = Book.builder().isbn("003-JN").title("World's History ").bookId("ISBN3333").unitPrice(new BigDecimal(30000)).build();
		
		jn.addBook(jn01);
		jn.addBook(jn02);
		jn.addBook(jn03);
		
		authorRepository.save(jn);
	}
	
	@Transactional
	public void insertNewBook() {
		Author author = authorRepository.findByName("Joana Nimar").orElseThrow();
		
		Book book = Book.builder().isbn("004-JN").title("History Details").bookId("ISBN4444").unitPrice(new BigDecimal(10000)).build();
		
		if(bookRepository.existsByBookId(book.getBookId())) {
			throw new RuntimeException(book.getBookId()+" already exists");
		}
		authorRepository.save(author);
//		authorRepository.flush();
	}
	
	@Transactional
	public void deleteLastBook() {
		Author author = authorRepository.findByName("Joana Nimar").orElseThrow();
		List<Book> books = author.getBooks();
		
		author.removeBook(books.get(books.size() - 1)); //마지막 도서 삭제
		
		authorRepository.save(author);
		
		
	}
}
