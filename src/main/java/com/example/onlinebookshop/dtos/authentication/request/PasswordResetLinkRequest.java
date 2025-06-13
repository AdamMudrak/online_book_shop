package com.example.onlinebookshop.dtos.authentication.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PasswordResetLinkRequest(
        @Schema(name = "emailOrUsername",
                example = "example@gmail.com or ExampleUsername",
                requiredMode = REQUIRED)
        @NotBlank
        String emailOrUsername){}
