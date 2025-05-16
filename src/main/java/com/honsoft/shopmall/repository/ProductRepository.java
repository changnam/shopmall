package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Computer;
import com.honsoft.shopmall.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	// You can define common methods here
    List<Product> findByCategory(String category);

    @Query("select c from Computer c where c.processor = :processor")
    List<Computer> findComputersByProcessor(@Param("processor") String processor);
}
