package com.honsoft.shopmall.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	private final AuthorService authorService;
	
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}
	
	@GetMapping("/insertwithbooks")
	public ResponseEntity<Object> insertWithBooks(){
		authorService.insertAuthorWithBooks();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String("3건 추가됨"));
	}
	
	@GetMapping("/insertnewbook")
	public ResponseEntity<Object> insertNewBook(){
		authorService.insertNewBook();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String("1건 추가됨"));
	}
	
	@GetMapping("/deletelastbook")
	public ResponseEntity<Object> deleteLastBook(){
		authorService.deleteLastBook();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String("1건 삭제됨"));
	}
}

