package com.example.onlinebookshop.dtos.cartitem.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateCartItemDto(
        @Schema(name = "bookId", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Positive
        @Digits(integer = 19, fraction = 0)
        @Max(9223372036854775807L)
        Long bookId,
        @Schema(name = "quantity",
                example = "25",
                description = "Quantity is limited to a 100 items per client. For more items, "
                        + "please contact us by phone or email.",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Positive
        @Digits(integer = 3, fraction = 0)
        @Max(100)
        Integer quantity) {
}
