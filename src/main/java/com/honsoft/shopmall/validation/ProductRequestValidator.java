package com.honsoft.shopmall.validation;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.honsoft.shopmall.dto.ProductRequest;
@Component
public class ProductRequestValidator implements Validator {
	
	private final MessageSource messageSource;
	
	public ProductRequestValidator(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ProductRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ProductRequest productRequest = (ProductRequest) target;
		
		Locale locale = LocaleContextHolder.getLocale(); // ✅ 현재 요청 Locale 가져오기
        String errorMessage = messageSource.getMessage("error.name.blank", null, locale);
        
		if(productRequest.email() == null || productRequest.email().trim().isEmpty()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", null, errorMessage);
		}
	}

}
