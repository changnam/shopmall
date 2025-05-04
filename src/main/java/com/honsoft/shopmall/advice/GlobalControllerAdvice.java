package com.honsoft.shopmall.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.validator.BookRequestValidator;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
public class GlobalControllerAdvice {

	private final BookRequestValidator bookRequestValidator;
	
	public GlobalControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}
	
	@ExceptionHandler(RuntimeException.class)  
	   private ModelAndView handleErrorCommon(Exception e) {
	     ModelAndView modelAndView = new ModelAndView(); 
	     modelAndView.addObject("exception", e );  
	     modelAndView.setViewName("errorCommon");
	     return modelAndView; 
	   }
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null && BookRequest.class.isAssignableFrom(target.getClass())) {
            binder.addValidators(bookRequestValidator);
        }
    }
}
