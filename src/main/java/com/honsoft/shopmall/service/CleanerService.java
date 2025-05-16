package com.honsoft.shopmall.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.honsoft.shopmall.dto.CleanerDto;

public interface CleanerService {
	CleanerDto createCleaner(CleanerDto cleanerDto);
	List<CleanerDto> getAllCleaners();
	Page<CleanerDto> getPageCleaners(Pageable pageable);
	List<CleanerDto> getByPowerRatingGreaterThan(Integer powerRating);
}
