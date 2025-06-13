package com.example.onlinebookshop.dtos.category.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateCategoryDto(
        @Schema(name = "name",
        example = "detective",
        description = "Name of the category. Must not be blank",
        requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name,

        @Schema(name = "description",
        example = "Any text",
        description = "Any text up to 512 chars including whitespaces")
        String description){}
