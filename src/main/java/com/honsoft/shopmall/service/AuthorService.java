package com.honsoft.shopmall.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.AlreadyExistsException;
import com.honsoft.shopmall.repository.AuthorRepository;
import com.honsoft.shopmall.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;

	public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	@Transactional
	public int insertAuthorWithBooks() {
		Author jn = authorRepository.findByName("Joana Nimar").orElse(null);
		int result = 0;
		if (jn == null)
			jn = Author.builder().name("Joana Nimar").age(24).genre("History").build();

		if (!bookRepository.existsByBookId("ISBN1111")) {
			Book jn01 = Book.builder().isbn("001-JN").title("A History of ancient prague").bookId("ISBN1111")
					.unitPrice(new BigDecimal(10000)).build();
			jn.addBook(jn01);
			result++;
		}
		if (!bookRepository.existsByBookId("ISBN2222")) {
			Book jn02 = Book.builder().isbn("002-JN").title("People's History").bookId("ISBN2222")
					.unitPrice(new BigDecimal(20000)).build();
			jn.addBook(jn02);
			result++;
		}
		if (!bookRepository.existsByBookId("ISBN3333")) {
			Book jn03 = Book.builder().isbn("003-JN").title("World's History ").bookId("ISBN3333")
					.unitPrice(new BigDecimal(30000)).build();
			jn.addBook(jn03);
			result++;
		}

		authorRepository.save(jn);
		return result;
	}

	@Transactional
	public void insertNewBook() {
		Author author = authorRepository.findByName("Joana Nimar").orElseThrow();

		if (bookRepository.existsByBookId("ISBN4444")) {
			Book book = Book.builder().isbn("004-JN").title("History Details").bookId("ISBN4444")
					.unitPrice(new BigDecimal(10000)).build();

			throw new AlreadyExistsException(book.getBookId() + " already exists");
		}

		authorRepository.save(author);
//		authorRepository.flush();
	}

	@Transactional
	public int deleteLastBook() {
		Author author = authorRepository.findByName("Joana Nimar").orElseThrow();
		List<Book> books = author.getBooks();
		if (books.size() > 0) {
			author.removeBook(books.get(books.size() - 1)); // 마지막 도서 삭제, book의 author_id 를 null 했으므로 orphanRemoval=true 이므로 save 시 해당 book 이 db에서 삭제됨

			authorRepository.save(author);
			return 1;
		}

		return 0;
	}
}
