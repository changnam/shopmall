package com.honsoft.shopmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
