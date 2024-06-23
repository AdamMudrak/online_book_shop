package com.example.onlinebookshop.dto.order.request;

import com.example.onlinebookshop.constants.dtoconstants.OrderDtoConstants;
import com.example.onlinebookshop.constants.dtoconstants.UserDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @Schema(name = UserDtoConstants.SHIPPING_ADDRESS,
                example = UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE,
                description = OrderDtoConstants.SHIPPING_ADDRESS_DESCRIPTION)
        @NotNull
        String shippingAddress) {
}
