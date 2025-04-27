package com.honsoft.shopmall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.honsoft.shopmall.controller")
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Model m) {
		m.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        m.addAttribute("error", "error");
        //m.addAttribute("message", e.getFieldErrors());
        BindingResult br = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (FieldError fe: br.getFieldErrors()) {
        	sb.append(fe.getDefaultMessage());
        }
        m.addAttribute("message",sb.toString());
		return "errorPage";
    }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleDefalultException(Exception e,Model m) {
		logger.info("handleException started.");
		
		m.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        m.addAttribute("error", "error");
        m.addAttribute("message", e.getMessage());
		return "errorPage";
	}
	
	

}
