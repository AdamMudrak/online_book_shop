package com.example.onlinebookshop.dtos.authentication.request;

import com.example.onlinebookshop.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
        @Schema(name = "emailOrUsername",
                example = "example@gmail.com or ExampleUsername",
                requiredMode = REQUIRED)
        @NotBlank
        String emailOrUsername,
        @Schema(name = "password",
                example = "Best_Password1@3$",
                description = Constants.PASSWORD_DESCRIPTION,
                requiredMode = REQUIRED)
        @NotBlank
        String password){}
