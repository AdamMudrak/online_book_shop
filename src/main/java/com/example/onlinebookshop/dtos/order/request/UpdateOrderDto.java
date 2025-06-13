package com.example.onlinebookshop.dtos.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderDto(
        @Schema(name = "status",
                example = "CREATED",
                description = """
            CREATED;
            PENDING_PAYMENT;
            IN_PROGRESS;
            SHIPPED;
            COMPLETED;
            CANCELLED;
            """,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String status) {
}
