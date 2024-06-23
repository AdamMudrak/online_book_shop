package com.example.onlinebookshop.dto.order.request;

import com.example.onlinebookshop.constants.dtoconstants.OrderDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StatusRequestDto(
        @Schema(name = OrderDtoConstants.STATUS_DTO,
                example = OrderDtoConstants.STATUS_DTO_EXAMPLE,
                description = OrderDtoConstants.STATUS_DTO_RULES,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Positive
        @Digits(integer = 1, fraction = 0)
        @Min(1)
        @Max(6)
        Integer status) {
}
