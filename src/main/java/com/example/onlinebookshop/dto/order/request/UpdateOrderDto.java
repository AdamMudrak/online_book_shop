package com.example.onlinebookshop.dto.order.request;

import com.example.onlinebookshop.constants.dto.OrderDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderDto(
        @Schema(name = OrderDtoConstants.STATUS_DTO,
                example = OrderDtoConstants.STATUS_DTO_EXAMPLE,
                description = OrderDtoConstants.STATUS_DTO_RULES,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String status) {
}
