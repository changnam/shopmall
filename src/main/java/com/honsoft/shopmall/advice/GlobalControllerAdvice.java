package com.honsoft.shopmall.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.honsoft.shopmall.validation.BookRequestValidator;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
public class GlobalControllerAdvice {

	private final BookRequestValidator bookRequestValidator;
	
	public GlobalControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}
	
	
	@InitBinder("bookRequest")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(bookRequestValidator);
	}
}
