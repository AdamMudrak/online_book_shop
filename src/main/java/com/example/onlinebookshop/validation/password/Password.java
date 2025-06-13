package com.example.onlinebookshop.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.onlinebookshop.constants.validation.ValidationConstants.INVALID_PASSWORD;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default INVALID_PASSWORD;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
