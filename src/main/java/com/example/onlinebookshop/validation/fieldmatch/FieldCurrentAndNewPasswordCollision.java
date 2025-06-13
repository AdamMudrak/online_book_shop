package com.example.onlinebookshop.validation.fieldmatch;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.onlinebookshop.constants.validation.ValidationConstants.PASSWORD_COLLISION;


@Constraint(validatedBy = FieldCurrentAndNewPasswordCollisionValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldCurrentAndNewPasswordCollision {
    String message() default PASSWORD_COLLISION;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
