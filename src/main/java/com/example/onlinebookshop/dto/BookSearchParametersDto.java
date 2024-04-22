package com.example.onlinebookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(
        @Min(0) @Digits(integer = 19, fraction = 2)
        BigDecimal fromPrice,
        @Min(1) @Digits(integer = 19, fraction = 2)
        BigDecimal toPrice,
        @NotBlank String[] titles,
        @NotBlank String[] authors
) {
}
