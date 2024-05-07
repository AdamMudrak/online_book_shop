package com.example.onlinebookshop.dto.book;

import static com.example.onlinebookshop.constants.BookDtoConstants.AUTHOR_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.AUTHOR_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.CEILING_PRICE;
import static com.example.onlinebookshop.constants.BookDtoConstants.CEILING_PRICE_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.FLOOR_PRICE;
import static com.example.onlinebookshop.constants.BookDtoConstants.FLOOR_PRICE_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.TITLE_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.TITLE_EXAMPLE;

import com.example.onlinebookshop.validation.ValidPriceRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
@ValidPriceRange
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearchParametersDto {
    @Schema(example = FLOOR_PRICE, description = FLOOR_PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal fromPrice;
    @Schema(example = CEILING_PRICE, description = CEILING_PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal toPrice;
    @Schema(example = TITLE_EXAMPLE, description = TITLE_DESCRIPTION)
    private String[] titles;
    @Schema(example = AUTHOR_EXAMPLE, description = AUTHOR_DESCRIPTION)
    private String[] authors;
}
