package com.example.onlinebookshop.dto.cartitem.request;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.dtoconstants.CartItemDtoConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddCartItemDto(
        @Schema(name = CartItemDtoConstants.BOOK_ID, example = Constants.ID_EXAMPLE)
        @NotNull
        @Positive
        @Digits(integer = 19, fraction = 0)
        @Max(9223372036854775807L)
        Long bookId,
        @Schema(name = CartItemDtoConstants.QUANTITY,
                example = CartItemDtoConstants.QUANTITY_EXAMPLE,
                description = CartItemDtoConstants.QUANTITY_DESCRIPTION)
        @NotNull
        @Positive
        @Digits(integer = 3, fraction = 0)
        @Max(100)
        int quantity) {
}
