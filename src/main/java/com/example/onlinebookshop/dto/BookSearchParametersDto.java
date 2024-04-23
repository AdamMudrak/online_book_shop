package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.ValidPriceRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@ValidPriceRange
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(
        @Positive @Digits(integer = 17, fraction = 2)
        BigDecimal fromPrice,
        @Positive @Digits(integer = 17, fraction = 2)
        BigDecimal toPrice,
        String[] titles,
        String[] authors
) {
}
