package com.honsoft.shopmall.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.AlreadyExistsException;
import com.honsoft.shopmall.exception.NotFoundException;
import com.honsoft.shopmall.mapper.AuthorMapper;
import com.honsoft.shopmall.repository.AuthorRepository;
import com.honsoft.shopmall.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final AuthorMapper authorMapper;

	public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository,AuthorMapper authorMapper) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.authorMapper = authorMapper;
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
			author.removeBook(books.get(books.size() - 1)); // 마지막 도서 삭제, book의 author_id 를 null 했으므로 orphanRemoval=true
															// 이므로 save 시 해당 book 이 db에서 삭제됨

			authorRepository.save(author);
			return 1;
		}

		return 0;
	}

	public Page<Book> getBooksOfAuthorByName(String name) {
		Page<Book> books = bookRepository.getBooksByAuthorName(name,
				PageRequest.of(0, 2, Sort.by(Sort.DEFAULT_DIRECTION.ASC, "title")));

		return books;

	}

	@Transactional
	public int fetchAllBooksByAuthorNameAndAddNewBook(String name) {
		List<Book> books = bookRepository.getBooksByAuthorName(name);
		int result = 0;
		if (!bookRepository.existsById("ISBN5555")) {
			Book book = Book.builder().isbn("005-JN").title("A History facts").bookId("ISBN5555")
					.unitPrice(new BigDecimal(10000)).build();
			result++;
			book.setAuthor(books.get(0).getAuthor());
			books.add(bookRepository.save(book));
		}

		return result;

	}
	
	public AuthorDto createAuthorWithBooks(AuthorDto dto) {
//        Author author = Author.toEntity(dto);
//        Author saved = authorRepository.save(author);
//        return saved.toDto();
        return null;
    }

	public AuthorDto getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
//        return author.toDto();
        return null;
    }

	public AuthorDto updateAuthor(Long id, AuthorDto dto) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        existing.setName(dto.getName()); //dto 의 항목들을 entity 의 항목에 업데이트
        existing.getBooks().clear();
        for (BookDto bookDto : dto.getBooks()) {
            Book book = new Book();
            book.setTitle(bookDto.getTitle());  //book dto 의 내용을 book entity 로 셋팅
            book.setEauthor(existing);
            existing.getBooks().add(book);
        }

        Author updated = authorRepository.save(existing);
//        return updated.toDto();
        return null;
    }

	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}

    public Page<AuthorDto> getAllAuthors(Pageable pageable) {
//        return authorRepository.findAll(pageable).map(author -> author.toDto());
        return null;
    }
    
	public List<AuthorDto> getAllAuthors() {
//		return bookRepository.findAll().stream().map(book -> book.toDto()).collect(Collectors.toList());
		return authorMapper.toDtoList(authorRepository.findAll());
	}
	
}
