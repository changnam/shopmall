package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.NotFoundException;
import com.honsoft.shopmall.repository.AuthorRepository;
import com.honsoft.shopmall.repository.BookRepository;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService{
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	
	public BookServiceImpl(BookRepository bookRepository,AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
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
	
	 public BookDto createBook(BookDto dto) {
	        Author author = authorRepository.findById(dto.getAuthorId())
	                .orElseThrow(() -> new NotFoundException("Author not found"));

	        Book book = Book.toEntity(dto, author);
	        return bookRepository.save(book).toDto();
	    }

	    public BookDto getBook(String bookId) {
	        Book book = bookRepository.findById(bookId)
	                .orElseThrow(() -> new NotFoundException("Book not found"));
	        return book.toDto();
	    }

	    public BookDto updateBook(String bookId, BookDto dto) {
	        Book existing = bookRepository.findById(bookId)
	                .orElseThrow(() -> new NotFoundException("Book not found"));

	        Author author = authorRepository.findById(dto.getAuthorId())
	                .orElseThrow(() -> new NotFoundException("Author not found"));

	        existing.setTitle(dto.getTitle());
	        existing.setEauthor(author);

	        return bookRepository.save(existing).toDto();
	    }

	    public void deleteBook(String bookId) {
	        bookRepository.deleteById(bookId);
	    }

	    public Page<BookDto> getAllBooks(Pageable pageable) {
	        return bookRepository.findAll(pageable).map(book -> book.toDto());
	    }

		@Override
		public List<BookDto> getAllBooks() {
			return bookRepository.findAll().stream().map(book -> book.toDto()).collect(Collectors.toList());
			
		}
}
