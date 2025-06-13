package com.example.onlinebookshop.dtos.book.request;

import com.example.onlinebookshop.constants.dto.BookDtoConstants;
import com.example.onlinebookshop.validation.price.ValidPriceRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@ValidPriceRange
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(
        @Schema(example = BookDtoConstants.FLOOR_PRICE,
        description = BookDtoConstants.FLOOR_PRICE_DESCRIPTION)
        @Digits(integer = 17, fraction = 2)
        @Positive BigDecimal fromPrice,

        @Schema(example = BookDtoConstants.CEILING_PRICE,
        description = BookDtoConstants.CEILING_PRICE_DESCRIPTION)
        @Digits(integer = 17, fraction = 2)
        @Positive BigDecimal toPrice,

        @Schema(example = BookDtoConstants.TITLE_EXAMPLE,
        description = BookDtoConstants.TITLE_DESCRIPTION)
        String[] titles,

        @Schema(example = BookDtoConstants.AUTHOR_EXAMPLE,
        description = BookDtoConstants.AUTHOR_DESCRIPTION)
        String[] authors){}
