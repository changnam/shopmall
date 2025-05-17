package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.mapper.CleanerMapper;
import com.honsoft.shopmall.mapper.MappingContext;
import com.honsoft.shopmall.repository.CleanerRepository;


@Service
public class CleanerServiceImpl implements CleanerService {
	private static final Logger logger = LoggerFactory.getLogger(CleanerServiceImpl.class);

	private final CleanerRepository cleanerRepository;
	private final CleanerMapper cleanerMapper;

	public CleanerServiceImpl(CleanerRepository cleanerRepository, CleanerMapper cleanerMapper) {
		this.cleanerRepository = cleanerRepository;
		this.cleanerMapper = cleanerMapper;
	}

	@Transactional
	@Override
	public CleanerDto createCleaner(CleanerDto cleanerDto) {
		Cleaner cleaner = cleanerMapper.toEntity(cleanerDto, new MappingContext(false));
        
		Cleaner saved = cleanerRepository.save(cleaner);
		CleanerDto savedDto = cleanerMapper.toDto(saved);
		return savedDto;
	}

	@Override
	public List<CleanerDto> getAllCleaners() {
		List<Cleaner> cleaners = cleanerRepository.findAll();
		List<CleanerDto> dtos = cleanerMapper.toDtoList(cleaners);
		return dtos;
	}

	@Override
	public List<CleanerDto> getByPowerRatingGreaterThan(Integer powerRating) {
		List<Cleaner> cleaners = cleanerRepository.findByPowerRatingGreaterThan(powerRating);
		List<CleanerDto> dtos = cleanerMapper.toDtoList(cleaners);
		return dtos;
	}

	@Override
	public Page<CleanerDto> getPageCleaners(Pageable pageable) {
		Page<Cleaner> cleaners = cleanerRepository.findAll(pageable);
		Page<CleanerDto> dtos = cleanerMapper.toPage(cleaners);
		return dtos;
	}

}
