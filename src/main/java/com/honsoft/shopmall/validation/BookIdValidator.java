package com.honsoft.shopmall.validation;

import org.springframework.stereotype.Component;

import com.honsoft.shopmall.dto.BookResponse;
import com.honsoft.shopmall.service.BookService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class BookIdValidator implements ConstraintValidator<BookId, String> {

	private final BookService bookService;

	public BookIdValidator(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public void initialize(BookId constraintAnnotation) {
		// TODO Auto-generated method stub
//		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String bookId, ConstraintValidatorContext context) {
		BookResponse book;
		try {
			book = bookService.getBookById(bookId);
		} catch (RuntimeException e) {
			return true;
		}
		if (book != null) {
			return false;
		}
		return true;
	}
}
