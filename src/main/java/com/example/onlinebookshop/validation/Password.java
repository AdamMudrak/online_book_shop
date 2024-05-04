package com.example.onlinebookshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default """
                    Invalid password
                    
                    Your password should contain:
                    1) at least one lowercase letter;
                    2) at least one uppercase letter;
                    3) at least one number;
                    4) at least one special character;
                    5) from 8 to 32 characters.""";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
