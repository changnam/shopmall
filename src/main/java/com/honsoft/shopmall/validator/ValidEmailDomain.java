package com.honsoft.shopmall.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EmailDomainValidator.class) // 이게 반드시 있어야 함
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailDomain {
    String message() default "{error.name.blank}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
