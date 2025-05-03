package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class BookRepositoryContext {
	 @PersistenceContext
	    private EntityManager entityManager;
	    
	    
	   	  public List<Book> selectMethod(){
	   		String jpql = "SELECT entity FROM Book entity";
	    	Query query = entityManager.createQuery(jpql);
	    	List<Book>  book = query.getResultList();   
	    	return book;
	    }   	  
	
}
