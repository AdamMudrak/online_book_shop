package com.example.onlinebookshop.validation.fieldmatch;

import com.example.onlinebookshop.dtos.authentication.request.PasswordChangeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldCurrentAndNewPasswordCollisionValidator implements ConstraintValidator<
        FieldCurrentAndNewPasswordCollision, PasswordChangeRequest> {
    @Override
    public boolean isValid(PasswordChangeRequest userSetNewPasswordRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (userSetNewPasswordRequestDto.currentPassword() == null
                || userSetNewPasswordRequestDto.newPassword() == null) {
            return false;
        }
        return !userSetNewPasswordRequestDto.currentPassword()
                .equals(userSetNewPasswordRequestDto.newPassword());
    }
}
