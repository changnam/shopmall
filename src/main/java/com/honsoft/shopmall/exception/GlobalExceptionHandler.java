package com.honsoft.shopmall.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Model m) {
		
		List<FieldErrorDetail> errors = e.getBindingResult().getFieldErrors().stream().map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage())).collect(Collectors.toList());
		
		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors);
		
		m.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        m.addAttribute("error", "error");
        //m.addAttribute("message", e.getFieldErrors());
//        BindingResult br = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (FieldErrorDetail fe: errors) {
        	sb.append(fe.field()+":"+fe.message());
        }
        m.addAttribute("message",sb.toString());
		return "errorPage";
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
	public String handleConstraintViolationException(ConstraintViolationException e, Model m) {
		List<FieldErrorDetail> errors = e.getConstraintViolations().stream().map(v -> new FieldErrorDetail(v.getPropertyPath().toString(), v.getMessage())).collect(Collectors.toList());
		
		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors);
		
		m.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        m.addAttribute("error", "error");
        //m.addAttribute("message", e.getFieldErrors());
//        BindingResult br = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (FieldErrorDetail fe: errors) {
        	sb.append(fe.field()+":"+fe.message());
        }
        m.addAttribute("message",sb.toString());
		return "errorPage";
	}
//
//	@ExceptionHandler(Exception.class)
////	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public String handleDefalultException(Exception e,Model m) {
//		logger.info("handleException started.");
//		
//		m.addAttribute("status", HttpStatus.BAD_REQUEST.value());
//        m.addAttribute("error", "error");
//        m.addAttribute("message", e.getMessage());
//		return "errorPage";
//	}
//	
//	

}
