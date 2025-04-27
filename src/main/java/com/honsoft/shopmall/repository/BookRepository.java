package com.honsoft.shopmall.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	@Query("select b from Book b")
	List<Book> getAllBookList();
	
	@Query("select b from Book b where bookId = :bookId")
	Book getBookById(@Param("bookId")String bookId);
	
	@Query("select b from Book b where category like :category%")
	List<Book> getBooksByCategory(@Param("category") String category);
	
	@Modifying
    @Query("UPDATE Book b SET b.description = :description WHERE b.id = :id")
    void updateBookById(@Param("id") Long id, @Param("description") String description);

    @Modifying
    @Query("DELETE FROM Book b WHERE b.bookId = :bookId")
    void deleteByBookId(@Param("bookId") String bookId);
    
    Optional<Book> findByBookId(String BookId);
	
}
