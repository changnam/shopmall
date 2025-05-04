package com.honsoft.shopmall.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.validator.BookRequestValidator;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
public class GlobalControllerAdvice {

	private final BookRequestValidator bookRequestValidator;
	
	public GlobalControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null && BookRequest.class.isAssignableFrom(target.getClass())) {
            binder.addValidators(bookRequestValidator);
        }
    }
}
