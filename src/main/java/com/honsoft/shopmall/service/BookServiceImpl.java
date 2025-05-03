package com.honsoft.shopmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.dto.ProductDetailRequest;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.entity.ProductDetail;
import com.honsoft.shopmall.repository.BookRepository;
import com.honsoft.shopmall.repository.BookRepositoryContext;
import com.honsoft.shopmall.repository.ProductDetailRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class BookServiceImpl implements BookService {
	private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
	private final BookRepository bookRepository;
	private final BookRepositoryContext bookRepositoryContext;
	private final ProductDetailRepository productDetailRepository;
	
	private final Validator validator;
	
	private final JdbcTemplate jdbcTemplate;
	
	@Value("${file.uploadDir}")
	String fileDir;

	public BookServiceImpl(BookRepository bookRepository,Validator validator, JdbcTemplate jdbcTemplate,BookRepositoryContext bookRepositoryContext,ProductDetailRepository productDetailRepository) {
		this.bookRepository = bookRepository;
		this.validator = validator;
		this.jdbcTemplate = jdbcTemplate;
		this.bookRepositoryContext = bookRepositoryContext;
		this.productDetailRepository = productDetailRepository;
	}

	@Override
	public List<BookResponse> getAllBookList() {
		// TODO Auto-generated method stub
//		List<Book> list = bookRepository.getAllBookList();
		List<Book> list = bookRepositoryContext.selectMethod();
		
		list.stream().forEach((book) -> logger.info(book.getId()+","+book.getCreatedDate()));
		
		List<ProductDetail> productDetailList = productDetailRepository.findAll();
		productDetailList.stream().forEach((detail) -> logger.info(detail.getId()+","+detail.getProduct().getName()));
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
		book.setProductCondition(bookRequest.productCondition());
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
	public BookResponse insertBook(BookRequest bookRequest, ProductDetailRequest detailRequest) throws IllegalStateException, IOException {
		
		Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		Optional<Book> checkBook = bookRepository.findByBookId(bookRequest.bookId());

		if (checkBook.isEmpty()) {
			Book targetBook = Book.from(bookRequest);
			ProductDetail productDetail = ProductDetail.from(detailRequest);
			if (productDetail.getProduct() != null)
				logger.info("product info from productDetail 1: "+productDetail.getProduct().getName());
			targetBook.setProductDetail(productDetail);
			productDetail.setProduct(targetBook);
			
			if (productDetail.getProduct() != null)
				logger.info("product info from productDetail 2: "+productDetail.getProduct().getName());
			
			if (!bookRequest.bookImage().isEmpty()) {
				String fileName = bookRequest.bookImage().getOriginalFilename();
				File savedFile = new File(fileDir, fileName);
				bookRequest.bookImage().transferTo(savedFile);
				targetBook.setFileName(fileName);
			}
			Book savedBook = bookRepository.save(targetBook);
			ProductDetail savedProductDetail = productDetailRepository.save(productDetail);
			
			return savedBook.toBookResponse();
		} else {
			throw new RuntimeException("bookId " + bookRequest.bookId() + " already exists");
		}
	}

}
