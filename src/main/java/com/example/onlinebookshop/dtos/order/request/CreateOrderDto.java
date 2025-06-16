package com.example.onlinebookshop.dtos.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDto(
        @Schema(name = "shippingAddress",
                example = "132, My Street, Kingston, New York 12401")
        @NotBlank
        String shippingAddress) {
}
