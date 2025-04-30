package com.honsoft.shopmall.validation;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.honsoft.shopmall.dto.BookRequest;

@Component
public class BookRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BookRequest.class.isAssignableFrom(clazz); // 해당 클래스만 검증
    }

    @Override
    public void validate(Object target, Errors errors) {
    	BookRequest book = (BookRequest) target;

        if (book.name() == null || book.name().isBlank()) {
            errors.rejectValue("name", "required.name", "Name is required");
        }

        BigDecimal bd1 = new BigDecimal("0");
        BigDecimal bd3 = new BigDecimal("500");
        
        if (book.unitPrice().compareTo(bd1) < 0 || book.unitPrice().compareTo(bd3) > 0) {
            errors.rejectValue("unitPrice", "invalid.unitPrice", "unitPrice must be between 0 and 500");
        }
    }
}
