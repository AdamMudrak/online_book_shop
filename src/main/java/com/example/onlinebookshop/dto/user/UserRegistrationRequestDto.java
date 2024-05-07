package com.example.onlinebookshop.dto.user;

import static com.example.onlinebookshop.constants.UserDtoConstants.EMAIL;
import static com.example.onlinebookshop.constants.UserDtoConstants.EMAIL_EXAMPLE;
import static com.example.onlinebookshop.constants.UserDtoConstants.FIRST_NAME;
import static com.example.onlinebookshop.constants.UserDtoConstants.FIRST_NAME_EXAMPLE;
import static com.example.onlinebookshop.constants.UserDtoConstants.LAST_NAME;
import static com.example.onlinebookshop.constants.UserDtoConstants.LAST_NAME_EXAMPLE;
import static com.example.onlinebookshop.constants.UserDtoConstants.PASSWORD;
import static com.example.onlinebookshop.constants.UserDtoConstants.PASSWORD_DESCRIPTION;
import static com.example.onlinebookshop.constants.UserDtoConstants.PASSWORD_EXAMPLE;
import static com.example.onlinebookshop.constants.UserDtoConstants.REPEAT_PASSWORD;
import static com.example.onlinebookshop.constants.UserDtoConstants.REPEAT_PASSWORD_DESCRIPTION;
import static com.example.onlinebookshop.constants.UserDtoConstants.REPEAT_PASSWORD_EXAMPLE;
import static com.example.onlinebookshop.constants.UserDtoConstants.SHIPPING_ADDRESS;
import static com.example.onlinebookshop.constants.UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

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
    @Schema(name = EMAIL, example = EMAIL_EXAMPLE, requiredMode = REQUIRED)
    @NotBlank
    @Email
    private String email;
    @Schema(name = PASSWORD, example = PASSWORD_EXAMPLE, description = PASSWORD_DESCRIPTION,
            requiredMode = REQUIRED)
    @Size(min = 8, max = 32)
    @NotBlank
    @Password
    private String password;
    @Schema(name = REPEAT_PASSWORD, example = REPEAT_PASSWORD_EXAMPLE,
            description = REPEAT_PASSWORD_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotBlank
    private String repeatPassword;
    @Schema(name = FIRST_NAME, example = FIRST_NAME_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String firstName;
    @Schema(name = LAST_NAME, example = LAST_NAME_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String lastName;
    @Schema(name = SHIPPING_ADDRESS, example = SHIPPING_ADDRESS_EXAMPLE)
    private String shippingAddress;
}
