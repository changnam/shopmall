package com.honsoft.shopmall.exception;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "com.honsoft.shopmall.restcontroller")
public class ApiGlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(ApiGlobalExceptionHandler.class);

	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String fileNotFoundException(FileNotFoundException e) {
		return "File not found.";/* Define your own message/response object */
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		List<FieldErrorDetail> errors = e.getBindingResult().getFieldErrors().stream()
				.map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage())).collect(Collectors.toList());

		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(), errors);

		StringBuffer sb = new StringBuffer();
		for (FieldErrorDetail fe : errors) {
			sb.append(fe.field() + ":" + fe.message());
		}

		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, sb.toString());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
		List<FieldErrorDetail> errors = e.getConstraintViolations().stream()
				.map(v -> new FieldErrorDetail(v.getPropertyPath().toString(), v.getMessage()))
				.collect(Collectors.toList());

		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(), errors);

		StringBuffer sb = new StringBuffer();
		for (FieldErrorDetail fe : errors) {
			sb.append(fe.field() + ":" + fe.message());
		}

		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, sb.toString());
	}

	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<ProblemDetail> handleTransactionSystemException(TransactionSystemException ex) {
		Throwable cause = ex.getRootCause(); // Spring utility method

		if (cause instanceof ConstraintViolationException cve) {
			List<FieldErrorDetail> errors = cve.getConstraintViolations().stream()
					.map(v -> new FieldErrorDetail(v.getPropertyPath().toString(), v.getMessage())).toList();

			String detail = errors.stream().map(e -> e.field() + ": " + e.message()).collect(Collectors.joining(", "));

			ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
			return ResponseEntity.badRequest().body(pd);
		}

		// fallback
		return ResponseEntity.badRequest()
				.body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> handleException(Exception e) {
		Throwable root = e;
		while (root.getCause() != null) {
			root = root.getCause();
		}
		logger.error("Root cause: ", root); // or System.out.println

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setDetail(root.getMessage());
		return ResponseEntity.badRequest().body(problemDetail);

//		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}

}
