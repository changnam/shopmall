package com.honsoft.shopmall.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

 
@Repository
public class MemberRepositoryContext {
    @PersistenceContext
    private EntityManager entityManager;
    
    
   	  public List<Member> selectMethod(){
   		String jpql = "SELECT entity FROM Member entity";
    	Query query = entityManager.createQuery(jpql);
    	List<Member>  member = query.getResultList();   
    	return member;
    }   	  
   	
		
	 public Member selectMethodById(Long id) {
		 String jpql = "SELECT entity FROM Member entity WHERE id =:e_id";
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_id", id);
		 Member  member =(Member) query.getSingleResult();		
		 return member;
	 }
		
	
	 @Transactional
	 public void insertMethod(String name,int age, String email) {		
		 String jpql = "INSERT INTO Member(name, age, email,createdDate,createdAt)  VALUES(:e_name, :e_age, :e_email, :e_createdDate, :e_createdAt)";
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_name", name);
		 query.setParameter("e_age", age);
		 query.setParameter("e_email", email);
		 query.setParameter("e_createdDate", LocalDateTime.now());
		 query.setParameter("e_createdAt", LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant());
		 query.executeUpdate();
		  
	 }
	 
	 @Transactional
	 public void insertMethod2(Member member) {		
		 String jpql = "INSERT INTO Member(name, age, email,createdDate,createdAt)  VALUES(:e_name, :e_age, :e_email, :e_createdDate, :e_createdAt)";
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_name", member.getName());
		 query.setParameter("e_age", member.getAge());
		 query.setParameter("e_email", member.getEmail());
		 query.setParameter("e_createdDate", member.getCreatedDate());
		 query.setParameter("e_createdAt", member.getCreatedAt());
		 query.executeUpdate();
		  
	 }
	 
	 @Transactional
	 public void insertMethod3(String name,int age,String email,LocalDateTime createdDate, Instant createdAt) {		
		 String jpql = "INSERT INTO Member(name, age, email,createdDate,createdAt)  VALUES(:e_name, :e_age, :e_email, :e_createdDate, :e_createdAt)";
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_name", name);
		 query.setParameter("e_age", age);
		 query.setParameter("e_email", email);
		 query.setParameter("e_createdDate", createdDate);
		 query.setParameter("e_createdAt", createdAt);
		 query.executeUpdate();
		  
	 }
	 
	 @Transactional
	 public void updateMethod(String name,int age, String email, Long id) {
		String jpql =  "UPDATE Member SET  name =:e_name, age =:e_age, email =:e_email WHERE id = :e_id";
			
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_name", name);
		 query.setParameter("e_age", age);	
		 query.setParameter("e_email", email);	
		 query.setParameter("e_id", id);	
		 query.executeUpdate();
	 }
	
	 
	
	@Transactional	
	 public void deleteMethod(Long id) {
		 String jpql = "DELETE FROM Member WHERE id = :e_id";
		 Query query = entityManager.createQuery(jpql);
		 query.setParameter("e_id", id);
		 query.executeUpdate();		 
	
	 };
	
    
}