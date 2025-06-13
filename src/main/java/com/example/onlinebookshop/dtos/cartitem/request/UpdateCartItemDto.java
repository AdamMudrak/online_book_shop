package com.example.onlinebookshop.dtos.cartitem.request;

import com.example.onlinebookshop.constants.dto.CartItemDtoConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCartItemDto(
        @Schema(name = CartItemDtoConstants.QUANTITY,
                example = CartItemDtoConstants.QUANTITY_EXAMPLE,
                description = CartItemDtoConstants.QUANTITY_DESCRIPTION,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Positive
        @Digits(integer = 3, fraction = 0)
        @Max(100)
        int quantity) {
}
