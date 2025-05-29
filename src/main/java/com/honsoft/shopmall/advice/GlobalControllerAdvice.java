package com.honsoft.shopmall.advice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.honsoft.shopmall.dto.BookRequest;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.validator.BookRequestValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
@Order(2)
public class GlobalControllerAdvice {

	private final BookRequestValidator bookRequestValidator;

	public GlobalControllerAdvice(BookRequestValidator bookRequestValidator) {
		this.bookRequestValidator = bookRequestValidator;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView handleConstraintViolation(ConstraintViolationException ex) {
//	        Map<String, String> errors = new HashMap<>();
//	        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//	            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//	        }
//	        return ResponseEntity.badRequest().body(errors);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.setViewName("errorCommon");
		return modelAndView;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Database constraint error: " + ex.getRootCause().getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.setViewName("errorCommon");
		return modelAndView;
	}

	@ExceptionHandler(TemplateInputException.class)
	public String handleTemplateInputException(TemplateInputException ex, Model model) {

		model.addAttribute("errorMessage", "Template Input Error: " + ex.getMessage());
		return "error/thymeleaf-error"; // Create this template
	}

	@ExceptionHandler(TemplateProcessingException.class)
	public String handleTemplateProcessingException(TemplateProcessingException ex, Model model) {
		model.addAttribute("errorMessage", "Template Processing Error: " + ex.getMessage());
		return "error/thymeleaf-error";
	}

	@ExceptionHandler(TransactionSystemException.class)
	public ModelAndView handleTransactionSystemException(HttpServletRequest request, TransactionSystemException ex) {

		Map<String, Object> error = new HashMap<>();
		Throwable cause = ex.getRootCause();
		if (cause instanceof ConstraintViolationException) {
			ConstraintViolationException validationEx = (ConstraintViolationException) cause;

			// Collect field errors into a map
			Map<String, Object> violations = validationEx.getConstraintViolations().stream().collect(Collectors
					.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(), (existing, replacement) -> existing // In
																															// case
																															// of
																															// duplicate
																															// keys
					));

			error.put("violations", violations);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.addObject("error", error);
		modelAndView.setViewName("errorCommon");

		return modelAndView;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex, Model model) {
//        model.addAttribute("errorMessage", "Unexpected Error: " + ex.getMessage());
//        return "error/generic-error"; // Optional generic fallback
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.setViewName("errorCommon");
		return modelAndView;
	}

	@ExceptionHandler(RuntimeException.class)
	private ModelAndView handleErrorCommon(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e);
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
