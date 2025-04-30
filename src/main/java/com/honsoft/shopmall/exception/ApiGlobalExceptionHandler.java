package com.honsoft.shopmall.exception;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "com.honsoft.shopmall.restcontroller")
public class ApiGlobalExceptionHandler {
	
	
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String fileNotFoundException(FileNotFoundException e) {
		return "File not found.";/* Define your own message/response object */
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		
		List<FieldErrorDetail> errors = e.getBindingResult().getFieldErrors().stream().map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage())).collect(Collectors.toList());
		
		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors);
		
        StringBuffer sb = new StringBuffer();
        for (FieldErrorDetail fe: errors) {
        	sb.append(fe.field()+":"+fe.message());
        }
        
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, sb.toString());
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
		List<FieldErrorDetail> errors = e.getConstraintViolations().stream().map(v -> new FieldErrorDetail(v.getPropertyPath().toString(), v.getMessage())).collect(Collectors.toList());
		
		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors);
		
		StringBuffer sb = new StringBuffer();
        for (FieldErrorDetail fe: errors) {
        	sb.append(fe.field()+":"+fe.message());
        }
        
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, sb.toString());
	}
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleException(Exception e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}

}
