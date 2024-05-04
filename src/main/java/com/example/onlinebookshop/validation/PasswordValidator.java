package com.example.onlinebookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_PATTERN = "(?=^.*[A-Z])(?=^.*[a-z])(?=^.*\\d)"
            + "(?=^.*[@$!%*?&])$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }
}
