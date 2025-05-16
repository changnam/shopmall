package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Cleaner;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long>{
	List<Cleaner> findByPowerRatingGreaterThan(int rating);
}
