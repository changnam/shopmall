package com.honsoft.shopmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByProductProductId(Long productId);
}
