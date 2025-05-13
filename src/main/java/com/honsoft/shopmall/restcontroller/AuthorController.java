package com.honsoft.shopmall.restcontroller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
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
	public ResponseEntity<Object> insertWithBooks() {
		int result = authorService.insertAuthorWithBooks();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String(result + " 건 추가됨"));
	}

	@GetMapping("/insertnewbook")
	public ResponseEntity<Object> insertNewBook() {
		authorService.insertNewBook();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String("1건 추가됨"));
	}

	@GetMapping("/deletelastbook")
	public ResponseEntity<Object> deleteLastBook() {
		int result = authorService.deleteLastBook();
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, new String(result + " 건 삭제됨"));
	}

	@GetMapping("/getbooksbyauthors")
	public ResponseEntity<Object> getBooksByAuthorName(@RequestParam("name") String name) {
		Page<Book> books = authorService.getBooksOfAuthorByName(name);
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, books);
	}

	@GetMapping("/fetchallbooksbyauthornameandaddnewbook")
	public ResponseEntity<Object> fetchAllBooksByAuthorNameAndAddNewBook(@RequestParam("name") String name) {
		int result = authorService.fetchAllBooksByAuthorNameAndAddNewBook(name);
		return ResponseHandler.responseBuilder("OK", HttpStatus.OK, result);
	}

	@PostMapping
	public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {
		return new ResponseEntity<>(authorService.createAuthorWithBooks(authorDto), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(authorService.getAuthor(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
		return ResponseEntity.ok(authorService.updateAuthor(id, authorDto));
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
