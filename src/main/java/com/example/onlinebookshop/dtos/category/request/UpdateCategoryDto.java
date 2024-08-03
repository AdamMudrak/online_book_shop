package com.example.onlinebookshop.dtos.category.request;

import com.example.onlinebookshop.constants.dtos.CategoryDtoConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCategoryDto(
        @Schema(name = CategoryDtoConstants.CATEGORY_NAME,
        example = CategoryDtoConstants.CATEGORY_EXAMPLE,
        description = CategoryDtoConstants.CATEGORY_NAME_DESCRIPTION)
        @NotBlank String name,

        @Schema(name = CategoryDtoConstants.CATEGORY_DESCRIPTION,
        example = CategoryDtoConstants.CATEGORY_DESCRIPTION_EXPLANATION,
        description = CategoryDtoConstants.CATEGORY_DESCRIPTION_EXPLANATION)
        String description){}
