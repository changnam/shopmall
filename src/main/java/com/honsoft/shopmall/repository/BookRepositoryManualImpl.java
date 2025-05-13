package com.honsoft.shopmall.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.BookIdException;

@Repository
public class BookRepositoryManualImpl implements BookRepositoryManual {
	
	
	@Autowired 
	private JdbcTemplate jdbcTemplate; 	
	
	
	public List<Book> getAllBookList() {
		String sql = "SELECT * FROM book"; 
	    List<Book> listOfBooks = jdbcTemplate.query(sql, new BookRowMapper());
			
		/*List<Book> listOfBooks = new ArrayList<>();
		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);			
		for (Map<String, Object> row : rows) {
		   Book book = new Book();
		   book.setBookId((String)row.get("b_bookId"));
		   book.setName((String)row.get("b_name"));
		   book.setUnitPrice(new BigDecimal((Integer)row.get("b_unitPrice")));
		   book.setAuthor((String)row.get("b_author"));
		   book.setDescription((String)row.get("b_description"));
		   book.setPublisher((String)row.get("b_publisher"));
		   book.setCategory((String)row.get("b_category"));
		   book.setUnitsInStock(new Long((String)row.get("b_unitsInStock")));   
		   book.setReleaseDate((String)row.get("b_releaseDate"));
		   book.setCondition((String)row.get("b_condition"));
		   book.setFileName((String)row.get("b_fileName"));
		   listOfBooks.add(book);	    
		}  
			*/
		return listOfBooks;
	
	}
	
	
	
	public List<Book> getBookListByCategory(String category) { 
	      List<Book> booksByCategory = new ArrayList<Book>(); 
	      String sql = "SELECT * FROM book where b_category LIKE '%" + category + "%'"; 
	      booksByCategory = jdbcTemplate.query(sql, new BookRowMapper());
	      
	    /*  List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);	
	      for (Map<String, Object> row : rows) {
	         Book book = new Book();
	         book.setBookId((String)row.get("b_bookId"));
	         book.setName((String)row.get("b_name"));
	         book.setUnitPrice(new BigDecimal((Integer)row.get("b_unitPrice")));
	         book.setAuthor((String)row.get("b_author"));
	         book.setDescription((String)row.get("b_description"));
	         book.setPublisher((String)row.get("b_publisher"));
	         book.setCategory((String)row.get("b_category"));
	         book.setUnitsInStock(new Long((String)row.get("b_unitsInStock")));
	     
	         book.setReleaseDate((String)row.get("b_releaseDate"));
	         book.setCondition((String)row.get("b_condition"));
	         book.setFileName((String)row.get("b_fileName"));
	         booksByCategory.add(book);	    
	      }
	      */
	      return booksByCategory;
	}
	
	
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		Set<Book> booksByPublisher = new HashSet<Book>();
		Set<Book> booksByCategory = new HashSet<Book>();

		Set<String> booksByFilter = filter.keySet();

		if (booksByFilter.contains("publisher")) {
			for (int j = 0; j < filter.get("publisher").size(); j++) { 
				 String pubisherName = filter.get("publisher").get(j); 
				 String sql = "SELECT * FROM book where b_publisher LIKE '%" + pubisherName + "%'"; 
				 List<Book> book = jdbcTemplate.query(sql, new BookRowMapper());
			     booksByPublisher.addAll(book);
			}
		}

		if (booksByFilter.contains("category")) { 
			for (int i = 0; i < filter.get("category").size(); i++) { 
				String category = filter.get("category").get(i); 
				String sql = "SELECT * FROM book where b_category LIKE '%" + category + "%'"; 				
				List<Book> list = jdbcTemplate.query(sql, new BookRowMapper());
				booksByCategory.addAll(list); 
			}
		} 

		booksByCategory.retainAll(booksByPublisher); 
		return booksByCategory; 
	   }
	
	
	
	public Book getBookById(String bookId) {
		Book bookInfo = null;
		String sql = "SELECT count(*) FROM book where b_bookId=?"; 
		int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
		if (rowCount != 0) {
		    sql = "SELECT * FROM book where b_bookId=?";  
		    bookInfo = jdbcTemplate.queryForObject(sql, new BookRowMapper(),bookId ); 
		}
		if (bookInfo== null)
			throw new BookIdException(bookId);
	
	     return bookInfo;
	  }	 
	
	
	
	public void setNewBook(Book book) { 
		String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate,b_condition, b_fileName) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(SQL, book.getBookId(), book.getName(), book.getUnitPrice(), book.getAuthor(),
			book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(),
			book.getReleaseDate(), book.getCondition(), book.getFileName());		
	} 
	
	public void setUpdateBook(Book book) { 
		if (book.getFileName() != null) {
			String SQL = "UPDATE Book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?,b_releaseDate = ?, b_condition = ?, b_fileName = ?  where b_bookId = ? ";
			jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(),
				book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
				book.getCondition(), book.getFileName(), book.getBookId());
			} else if (book.getFileName() == null) {
			String SQL = "UPDATE Book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, b_releaseDate = ?, b_condition = ?  where b_bookId = ? ";
			jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(),
				book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
				book.getCondition(), book.getBookId());
			}
		} 
	
	 public void setDeleteBook(String bookID) { 
	      String SQL = "DELETE from Book where b_bookId = ? "; 
	     jdbcTemplate.update(SQL, bookID);
	   }



	@Override
	public boolean existsByBookId(String bookId) {
		// TODO Auto-generated method stub
		Book bookInfo = null;
		String sql = "SELECT count(*) FROM book where b_bookId=?"; 
		int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
		if (rowCount != 0) {
		    sql = "SELECT * FROM book where b_bookId=?";  
		    bookInfo = jdbcTemplate.queryForObject(sql, new BookRowMapper(),bookId ); 
		}
		if (bookInfo== null)
			return false;
	
	     return true;
	}

}
