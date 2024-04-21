package com.example.onlinebookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PathToFileValidator implements ConstraintValidator<PathToFile, String> {
    private static final String PATTERN_OF_URL = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//"
            + "=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

    @Override
    public boolean isValid(String pathToFile,
                           ConstraintValidatorContext constraintValidatorContext) {
        return pathToFile != null && Pattern.compile(PATTERN_OF_URL).matcher(pathToFile).matches();
    }
}
