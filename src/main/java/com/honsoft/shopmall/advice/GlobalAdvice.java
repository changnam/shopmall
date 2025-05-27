package com.honsoft.shopmall.advice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.honsoft.shopmall.response.ResponseHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalAdvice {
	private static final Logger logger = LoggerFactory.getLogger(GlobalAdvice.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public Object handleNotFound(HttpServletRequest request, NoHandlerFoundException ex) {
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@ browser nohandler");
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			// API request
			Map<String, Object> error = new HashMap<>();
			error.put("error", "NoHandlerFoundException exception");
			error.put("status", HttpStatus.NOT_FOUND.value());
			error.put("httpStatus", HttpStatus.NOT_FOUND.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", ex.getRequestURL());
			return ResponseHandler.responseBuilder("error occured", HttpStatus.NOT_FOUND, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public Object handleNoResourceFoundException(HttpServletRequest request, NoResourceFoundException ex) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@ browser NoResourceFoundException");
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			// API request
			Map<String, Object> error = new HashMap<>();
			error.put("error", "NoResourceFoundException error");
			error.put("status", HttpStatus.NOT_FOUND.value());
			error.put("httpStatus", HttpStatus.NOT_FOUND.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", request.getRequestURL());
			return ResponseHandler.responseBuilder("error occured", HttpStatus.NOT_FOUND, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public Object handleAuthorizationDeniedException(HttpServletRequest request, AuthorizationDeniedException ex) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@ browser AuthorizationDeniedException:");
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			// API request
			Map<String, Object> error = new HashMap<>();
			error.put("error", "AuthorizationDeniedException error");
			error.put("status", HttpStatus.FORBIDDEN.value());
			error.put("httpStatus", HttpStatus.FORBIDDEN.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", request.getRequestURL());

			return ResponseHandler.responseBuilder("error occured", HttpStatus.FORBIDDEN, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public Object handleUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException ex) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@ browser AuthorizationDeniedException:");
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			// API request
			Map<String, Object> error = new HashMap<>();
			error.put("error", "UsernameNotFoundException error");
			error.put("status", HttpStatus.NOT_FOUND.value());
			error.put("httpStatus", HttpStatus.NOT_FOUND.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", request.getRequestURL());
			return ResponseHandler.responseBuilder("error occured", HttpStatus.NOT_FOUND, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}
	}

	@ExceptionHandler(Exception.class)
	public Object handleAllExceptions(HttpServletRequest request, Exception ex) {
		System.out.println(ex.getMessage());
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			// API request
			Map<String, Object> error = new HashMap<>();
			error.put("error", "UsernameNotFoundException error");
			error.put("status", HttpStatus.NOT_FOUND.value());
			error.put("httpStatus", HttpStatus.NOT_FOUND.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", request.getRequestURL());
			return ResponseHandler.responseBuilder("error occured", HttpStatus.NOT_FOUND, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}

	}

	@ExceptionHandler(TransactionSystemException.class)
	public Object handleConstraintViolationException(HttpServletRequest request, TransactionSystemException ex) {
		String accept = request.getHeader("Accept");
		String contentType = request.getHeader("Content-Type");
		String path = request.getServletPath();

		if (path.startsWith("/api/v")) {
			Map<String, Object> error = new HashMap<>();
			Throwable cause = ex.getRootCause();
			if (cause instanceof ConstraintViolationException) {
				ConstraintViolationException validationEx = (ConstraintViolationException) cause;

				// Collect field errors into a map
				Map<String, Object> violations = validationEx.getConstraintViolations().stream()
						.collect(Collectors.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(),
								(existing, replacement) -> existing // In case of duplicate keys
						));

				error.put("violations", violations);
			}
			error.put("error", "UsernameNotFoundException error");
			error.put("status", HttpStatus.NOT_FOUND.value());
			error.put("httpStatus", HttpStatus.NOT_FOUND.name());
			error.put("message", ex.getLocalizedMessage());
			error.put("data", null);
			error.put("path", request.getRequestURL());
			return ResponseHandler.responseBuilder("error occured", HttpStatus.NOT_FOUND, error);
//			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			Map<String, Object> error = new HashMap<>();
			Throwable cause = ex.getRootCause();
			if (cause instanceof ConstraintViolationException) {
				ConstraintViolationException validationEx = (ConstraintViolationException) cause;

				// Collect field errors into a map
				Map<String, Object> violations = validationEx.getConstraintViolations().stream()
						.collect(Collectors.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(),
								(existing, replacement) -> existing // In case of duplicate keys
						));

				error.put("violations", violations);
			}
			
			// Browser request (Accept: text/html or default)
//            ModelAndView modelAndView = new ModelAndView("error/404");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
//            modelAndView.addObject("url", ex.getRequestURL());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("error",error);
			modelAndView.setViewName("errorCommon");

			return modelAndView;
		}

	}

	@ModelAttribute("siteName")
	public String getSiteName(HttpServletRequest request) {
		String host = request.getServerName();
		return switch (host) {
		case "honsoft.co.kr" -> "Honsoft.co.kr";
		case "honsoft.com" -> "Honsoft.com";
		default -> host;
		};
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("global binder started.");
//		binder.registerCustomEditor(String.class, new EmptyStringToNullEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}