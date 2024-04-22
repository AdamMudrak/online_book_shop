package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.ValidPriceRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@ValidPriceRange
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(
        @Schema(example = "799.99", description = "Defines floor price")
        @Min(0) @Digits(integer = 17, fraction = 2)
        BigDecimal fromPrice,
        @Schema(example = "999.99", description = "Defines ceiling price."
                + " Must always be greater than floor price")
        @Min(0) @Digits(integer = 17, fraction = 2)
        BigDecimal toPrice,
        @Schema(example = "Harry Potter",
                description = "Must be exactly equal to the title in DB. "
                        + "You can specify many titles")
        String[] titles,
        @Schema(example = "J.K. Rowling",
                description = "Must be exactly equal to the author in DB. "
                        + "You can specify many authors")
        String[] authors
) {
}
