package com.example.onlinebookshop.dto.user;

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
    @Schema(name = "email", example = "example@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Email
    private String email;
    @Schema(name = "password", example = "Best_Password1@3$",
            description = """
                    Your password should contain:
                    1) at least one lowercase letter, like 'a';
                    2) at least one uppercase letter, like 'A';
                    3) at least one number, like '0';
                    4) at least one special character, like '?!@#$%^&*~';
                    5) from 8 to 32 characters.""",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 32)
    @NotBlank
    @Password
    private String password;
    @Schema(name = "repeatPassword", example = "Best_Password1@3$",
            description = "This field must be the same as password!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String repeatPassword;
    @Schema(name = "firstName", example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String firstName;
    @Schema(name = "lastName", example = "Wick",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String lastName;
    @Schema(name = "shippingAddress", example = "132, My Street, Kingston, New York 12401")
    private String shippingAddress;
}
