package com.honsoft.shopmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
}
