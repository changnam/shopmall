package com.honsoft.shopmall.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Order;

@Repository
public interface OrderProRepository extends JpaRepository<Order, Long> {
	
	   
	
	
}
