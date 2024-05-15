package com.example.onlinebookshop.dto.book.request;

import com.example.onlinebookshop.constants.BookDtoConstants;
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
    @Schema(example = BookDtoConstants.FLOOR_PRICE,
            description = BookDtoConstants.FLOOR_PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal fromPrice;
    @Schema(example = BookDtoConstants.CEILING_PRICE,
            description = BookDtoConstants.CEILING_PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal toPrice;
    @Schema(example = BookDtoConstants.TITLE_EXAMPLE,
            description = BookDtoConstants.TITLE_DESCRIPTION)
    private String[] titles;
    @Schema(example = BookDtoConstants.AUTHOR_EXAMPLE,
            description = BookDtoConstants.AUTHOR_DESCRIPTION)
    private String[] authors;
}
