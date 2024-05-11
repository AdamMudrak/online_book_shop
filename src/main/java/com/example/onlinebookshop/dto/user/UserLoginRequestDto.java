package com.example.onlinebookshop.dto.user;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.example.onlinebookshop.constants.UserDtoConstants;
import com.example.onlinebookshop.validation.Email;
import com.example.onlinebookshop.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Schema(name = UserDtoConstants.EMAIL, example = UserDtoConstants.EMAIL_EXAMPLE,
                requiredMode = REQUIRED)
        @NotBlank
        @Email String email,
        @Schema(name = UserDtoConstants.PASSWORD, example = UserDtoConstants.PASSWORD_EXAMPLE,
                description = UserDtoConstants.PASSWORD_DESCRIPTION,
                requiredMode = REQUIRED)
        @Size(min = 8, max = 32)
        @NotBlank
        @Password String password) {
}
