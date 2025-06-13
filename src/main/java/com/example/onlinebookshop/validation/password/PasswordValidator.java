package com.example.onlinebookshop.validation.password;

import static com.example.onlinebookshop.constants.validation.ValidationConstants.COMPILED_PASSWORD_PATTERN;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && COMPILED_PASSWORD_PATTERN.matcher(password).matches();
    }
}
