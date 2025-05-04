package com.honsoft.shopmall.validator;

import java.math.BigDecimal;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.honsoft.shopmall.dto.BookRequest;

@Component
public class BookRequestValidator implements Validator {

	private final MessageSource messageSource;
	
	public BookRequestValidator (MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return BookRequest.class.isAssignableFrom(clazz); // 해당 클래스만 검증
    }

    @Override
    public void validate(Object target, Errors errors) {
    	BookRequest book = (BookRequest) target;
    	var locale = LocaleContextHolder.getLocale(); // 현재 요청 Locale

        if (book.name() == null || book.name().isBlank()) {
            errors.rejectValue("name", "required.name", "Name is required");
        }

        BigDecimal bd1 = new BigDecimal("0");
        BigDecimal bd3 = new BigDecimal("500");
        
        if (book.unitPrice().compareTo(bd1) < 0 || book.unitPrice().compareTo(bd3) > 0) {
        	String msg = messageSource.getMessage("invalid.unitPrice", null, locale);
            errors.rejectValue("unitPrice", "invalid.unitPrice", msg);
        }
    }
}
