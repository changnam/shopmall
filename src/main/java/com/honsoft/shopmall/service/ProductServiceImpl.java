package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;
import com.honsoft.shopmall.repository.BookRepository;
import com.honsoft.shopmall.repository.ComputerRepository;
import com.honsoft.shopmall.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final ComputerRepository computerRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                          BookRepository bookRepository,
                          ComputerRepository computerRepository) {
        this.productRepository = productRepository;
        this.bookRepository = bookRepository;
        this.computerRepository = computerRepository;
    }

    public Optional<Product> getProductById(Long id) {
    	// 아래의 경우 id 가 1 인 products 테이블과 subtables (Book, Computer) 을 조회하여 반환한다. 즉, Book 또는 Computer
    	Product product = productRepository.findById(1L).orElseThrow();
    	
    	if (product instanceof Book book) {
    	    logger.info(book.getAuthor());
    	} else if (product instanceof Computer computer) {
    		logger.info(computer.getBrand());
    	}
    	
        return Optional.of(product);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Computer saveComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public void deleteProductById(Long id) {
    	productRepository.deleteById(id);
    }
}

