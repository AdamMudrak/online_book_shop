package com.example.onlinebookshop.dto.user.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.example.onlinebookshop.constants.UserDtoConstants;
import com.example.onlinebookshop.validation.Email;
import com.example.onlinebookshop.validation.FieldMatch;
import com.example.onlinebookshop.validation.Password;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldMatch
public class UserRegistrationRequestDto {
    @Schema(name = UserDtoConstants.EMAIL, example = UserDtoConstants.EMAIL_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    @Email
    private String email;
    @Schema(name = UserDtoConstants.PASSWORD, example = UserDtoConstants.PASSWORD_EXAMPLE,
            description = UserDtoConstants.PASSWORD_DESCRIPTION,
            requiredMode = REQUIRED)
    @Size(min = 8, max = 32)
    @NotBlank
    @Password
    private String password;
    @Schema(name = UserDtoConstants.REPEAT_PASSWORD,
            example = UserDtoConstants.REPEAT_PASSWORD_EXAMPLE,
            description = UserDtoConstants.REPEAT_PASSWORD_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotBlank
    private String repeatPassword;
    @Schema(name = UserDtoConstants.FIRST_NAME, example = UserDtoConstants.FIRST_NAME_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String firstName;
    @Schema(name = UserDtoConstants.LAST_NAME, example = UserDtoConstants.LAST_NAME_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String lastName;
    @Schema(name = UserDtoConstants.SHIPPING_ADDRESS,
            example = UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE)
    private String shippingAddress;
}
