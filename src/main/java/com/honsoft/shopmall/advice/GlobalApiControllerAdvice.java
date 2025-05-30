package com.honsoft.shopmall.advice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.exception.BizException;
import com.honsoft.shopmall.exception.UploadNotSupportedException;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.validator.BookRequestValidator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "com.honsoft.shopmall.restcontroller")
@Order(1)
public class GlobalApiControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(GlobalApiControllerAdvice.class);

	private final BookRequestValidator bookRequestValidator;

	public GlobalApiControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFoundException(HttpServletRequest request,
			NoHandlerFoundException ex) {

		return ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException e) {
		Map<String, Object> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors()
				.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
		return ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, errors);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
		return ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleErrorCommon(Exception e) {

		return ResponseHandler.responseBuilder("runtime error occured", HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExcetion(Exception e) {

		return ResponseHandler.responseBuilder("error occured", HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException e) {
		return ResponseHandler.responseBuilder("file max size error", HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(UploadNotSupportedException.class)
	public ResponseEntity<Object> handleUploadNotSupportedException(UploadNotSupportedException e) {
		return ResponseHandler.responseBuilder("upload file not supported", HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(BizException.class)
	public ResponseEntity<Object> handleBizException(BizException e) {
		Map<String, Object> error = new HashMap<>();
		error.put("code", e.getCode());
		error.put("message", e.getMessage());
		error.put("status", e.getStatus());
		return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.BAD_REQUEST, error);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		// Collect field errors into a map
		Map<String, String> errors = ex.getConstraintViolations().stream().collect(Collectors
				.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(), (existing, replacement) -> existing // In
																														// case
																														// of
																														// duplicate
																														// keys
				));

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Validation failed");
		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<Object> handleTransactionSystemException(HttpServletRequest request,
			TransactionSystemException ex) {

		Map<String, Object> error = new HashMap<>();
		Throwable cause = ex.getRootCause();
		if (cause instanceof ConstraintViolationException) {
			ConstraintViolationException validationEx = (ConstraintViolationException) cause;

			// Collect field errors into a map
			Map<String, Object> violations = validationEx.getConstraintViolations().stream().collect(Collectors
					.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(), (existing, replacement) -> existing // In
																															// case
																															// of
																															// duplicate																															// keys
					));

			error.put("violations", violations);
		}
		error.put("error", "TransactionSystemException error");
		error.put("status", HttpStatus.NOT_FOUND.value());
		error.put("httpStatus", HttpStatus.NOT_FOUND.name());
		error.put("message", ex.getLocalizedMessage());
		error.put("data", null);
		error.put("path", request.getRequestURL());
		return ResponseHandler.responseBuilder("error occured", HttpStatus.INTERNAL_SERVER_ERROR, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (target != null && BookRequest.class.isAssignableFrom(target.getClass())) {
			binder.addValidators(bookRequestValidator);
		}
	}
}
