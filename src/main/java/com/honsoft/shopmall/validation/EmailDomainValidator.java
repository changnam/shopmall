package com.honsoft.shopmall.validation;

import org.springframework.stereotype.Component;

import com.honsoft.shopmall.dto.ProductRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
@Component
public class EmailDomainValidator implements ConstraintValidator<ValidEmailDomain, String> {

	@Override
    public void initialize(ValidEmailDomain constraintAnnotation) {
        // 초기화 작업 필요 없으면 비워둬도 됨
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // null이나 blank는 기본 validation (@NotBlank)로 처리
        }
        
     // 추가 로직: 이메일 도메인이 "example.com"이어야만 통과
        if (!value.endsWith("@example.com")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email must end with @example.com")
                   .addPropertyNode("email") // 특정 필드에 에러를 걸기
                   .addConstraintViolation();
            return false;
        }
        
        return true;
    }
}