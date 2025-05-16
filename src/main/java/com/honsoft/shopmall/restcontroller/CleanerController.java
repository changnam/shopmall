package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.CleanerService;

@RestController
@RequestMapping("/api/v1/cleaners")
public class CleanerController {

	private final CleanerService cleanerService;

	public CleanerController(CleanerService cleanerService) {
		this.cleanerService = cleanerService;
	}

	@PostMapping
	public ResponseEntity<Object> createCleaner(@RequestBody CleanerDto cleanerDto) {
		CleanerDto created = cleanerService.createCleaner(cleanerDto);
		return ResponseHandler.responseBuilder("cleaner created", HttpStatus.OK, created);
	}

	@GetMapping
	public ResponseEntity<Object> getAllCleaners() {
		List<CleanerDto> list = cleanerService.getAllCleaners();
		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, list);
	}

	@GetMapping("/page")
	public ResponseEntity<Object> getPageCleaners(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<CleanerDto> page = cleanerService.getPageCleaners(pageable);
		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, page);
	}

}
