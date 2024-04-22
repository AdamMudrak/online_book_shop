package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.ValidPriceRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@ValidPriceRange
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(
        @Min(0) @Digits(integer = 17, fraction = 2)
        BigDecimal fromPrice,
        @Min(0) @Digits(integer = 17, fraction = 2)
        BigDecimal toPrice,
        String[] titles,
        String[] authors
) {
}
