package com.example.onlinebookshop.dto.order.request;

import com.example.onlinebookshop.constants.dto.UserDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDto(
        @Schema(name = UserDtoConstants.SHIPPING_ADDRESS,
                example = UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE)
        @NotBlank
        String shippingAddress) {
}
