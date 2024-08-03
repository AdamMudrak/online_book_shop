package com.example.onlinebookshop.validation;

import com.example.onlinebookshop.dtos.user.request.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto userRegistrationRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (userRegistrationRequestDto.password() == null
                || userRegistrationRequestDto.repeatPassword() == null) {
            return false;
        }
        return userRegistrationRequestDto.password()
                .equals(userRegistrationRequestDto.repeatPassword());
    }
}
