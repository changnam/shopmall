package com.honsoft.shopmall.restcontroller;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.honsoft.shopmall.restcontroller")
public class ApiGlobalExceptionHandler {
	
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String fileNotFoundException(FileNotFoundException e) {
		return "File not found.";/* Define your own message/response object */
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleException(Exception e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}

}
