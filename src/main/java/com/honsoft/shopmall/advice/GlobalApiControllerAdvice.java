package com.honsoft.shopmall.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.validator.BookRequestValidator;

@RestControllerAdvice(basePackages = "com.honsoft.shopmall.restcontroller")
public class GlobalApiControllerAdvice {

	private final BookRequestValidator bookRequestValidator;

	public GlobalApiControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException e) {
		Map<String, Object> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
		return  ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, errors);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleErrorCommon(Exception e) {

		return ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException e){
		return ResponseHandler.responseBuilder("file max size error", HttpStatus.BAD_REQUEST, e);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (target != null && BookRequest.class.isAssignableFrom(target.getClass())) {
			binder.addValidators(bookRequestValidator);
		}
	}
}
