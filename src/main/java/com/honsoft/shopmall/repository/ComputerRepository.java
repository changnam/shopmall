package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long>{

	List<Computer> findByBrand(String brand);
}
