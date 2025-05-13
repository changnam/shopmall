package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, String>{
	Boolean existsByBookId(String bookId);
	
	@Query("select b from Book b join b.eauthor a where a.name = :name")
	Page<Book> getBooksByAuthorName(@Param("name") String name, Pageable pageable);
	
	@Query("select b from Book b join b.eauthor a where a.name = :name")
	List<Book> getBooksByAuthorName(@Param("name") String name);

}
