package com.honsoft.shopmall.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.BoardFormDto;
import com.honsoft.shopmall.response.ResponseHandler;

@RestController
@RequestMapping("/api")
public class ReactController {

	@CrossOrigin(origins = "http://localhost:3000") // ✅ Allows only this frontend
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(){
		return ResponseHandler.responseBuilder("user saved", HttpStatus.OK, BoardFormDto.builder().title("테스트").build());
		
	}
}
