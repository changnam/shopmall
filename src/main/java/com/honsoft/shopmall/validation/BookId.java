package com.honsoft.shopmall.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = BookIdValidator.class) // 이게 반드시 있어야 함
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface BookId {
	   String message() default "";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
