package com.example.onlinebookshop.validation.emailandusername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.onlinebookshop.constants.validation.ValidationConstants.COMPILED_EMAIL_PATTERN;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email == null || COMPILED_EMAIL_PATTERN.matcher(email).matches();
    }
}
