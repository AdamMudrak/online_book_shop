package com.example.onlinebookshop.validation.fieldmatch;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.onlinebookshop.constants.validation.ValidationConstants.PASSWORD_MISMATCH;

@Constraint(validatedBy = FieldRegisterMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldRegisterMatch {
    String message() default PASSWORD_MISMATCH;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
