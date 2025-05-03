package com.honsoft.shopmall.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Member;

import jakarta.transaction.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	@Query(value="select m from Member m")
	//@Query(value = "select * from members",nativeQuery=true)
	List<Member> selectMethod();
	
	 //@Modifying 
	 @Query(value = "SELECT entity FROM Member entity WHERE id = :e_id")
	 //@Query(value = "SELECT * FROM Members WHERE id = ?", nativeQuery=true) 
	 Member  selectMethodById(@Param("e_id") Long id);	
		
	 @Transactional
	 @Modifying 
	//@Query(value = "INSERT INTO Member(name, age, email)  VALUES(:e_name, :e_age, :e_email, )")
	  @Query(value = "INSERT INTO Members(name, age, email,createdDate,createdAt) VALUES(?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery=true) 
	 int insertMethod(@Param("e_name") String name, @Param("e_age") int age, @Param("e_email") String email);
	
	 @Transactional
	 @Modifying 
	@Query(value = "INSERT INTO Member(name, age, email,createdDate,createdAt)  VALUES(:#{#entity.name}, :#{#entity.age}, :#{#entity.email}, :#{#entity.createdDate}, :#{#entity.createdAt})")
	  int insertMethod2(@Param("entity") Member member);
	 
	 @Transactional
	 @Modifying 
	@Query(value = "INSERT INTO Member(name, age, email,createdDate,createdAt)  VALUES(:e_name, :e_age, :e_email, :e_createdDate, :e_createdAt )")
	  int insertMethod3(@Param("e_name") String name, @Param("e_age") int age, @Param("e_email") String email, @Param("e_createdDate") LocalDateTime createdDate,@Param("e_createdAt") Instant createdAt );
	

	 @Transactional
	 @Modifying 
	 @Query(value = "UPDATE Member SET  name =:e_name, age =:e_age, email =:e_email WHERE id = :e_id")
	 // @Query(value = "UPDATE Members SET  name =?, age =?, email =? WHERE id = ?", nativeQuery=true)
	 int updateMethod(@Param("e_name") String name, @Param("e_age") int age, @Param("e_email") String email, @Param("e_id") Long id);
	 
	 
	 @Transactional
	 @Modifying 
	 @Query(value = "UPDATE Member SET  name =:#{#entity.name}, age =:#{#entity.age}, email =:#{#entity.email} WHERE id = :#{#entity.id}")	
	 int updateMethod2(@Param("entity") Member member);
	 
	
	 @Transactional
	 @Modifying 
	 @Query(value = "DELETE FROM Member WHERE id = :e_id")
	//@Query(value = "DELETE FROM Members WHERE id = ?", nativeQuery=true) 
	 int deleteMethod(@Param("e_id") Long id);


}
