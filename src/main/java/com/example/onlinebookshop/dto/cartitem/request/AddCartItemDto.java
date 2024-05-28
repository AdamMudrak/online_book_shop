package com.example.onlinebookshop.dto.cartitem.request;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllerconstants.ShopCartConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddCartItemDto(
        @Schema(name = ShopCartConstants.BOOK_ID, example = Constants.ID_EXAMPLE)
        @NotNull
        @Positive
        //todo make sure they are numbers
        Long bookId,
        @Schema(name = ShopCartConstants.QUANTITY, example = ShopCartConstants.QUANTITY_EXAMPLE)
        @NotNull
        @Positive
        int quantity) {
}
