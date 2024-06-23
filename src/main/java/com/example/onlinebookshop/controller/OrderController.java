package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllerconstants.OrderConstants;
import com.example.onlinebookshop.constants.dtoconstants.OrderDtoConstants;
import com.example.onlinebookshop.constants.dtoconstants.UserDtoConstants;
import com.example.onlinebookshop.dto.order.request.AddressDto;
import com.example.onlinebookshop.dto.order.request.StatusRequestDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.services.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<OrderDto> getOrdersByUserId(@AuthenticationPrincipal User user) {
        return orderService.getOrdersByUserId(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public OrderDto addOrder(@AuthenticationPrincipal User user,
                             @Valid
                             @RequestBody
                             @Parameter(name = UserDtoConstants.SHIPPING_ADDRESS,
                                     example = UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE,
                                     description = OrderDtoConstants.SHIPPING_ADDRESS_DESCRIPTION)
                             AddressDto addressDto) {
        return orderService.addOrder(user.getId(), addressDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @Valid
                                      @RequestBody
                                      @Parameter(name = OrderDtoConstants.STATUS_DTO,
                                              example = OrderDtoConstants.STATUS_DTO_EXAMPLE,
                                              description = OrderDtoConstants.STATUS_DTO_RULES)
                                      StatusRequestDto statusRequestDto) {
        return orderService.updateOrderStatus(orderId, statusRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> findOrderItemsByOrderId(
            @PathVariable
            @Parameter(name = Constants.ID,
                description = OrderConstants.VALID_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long orderId) {
        return orderService.findOrderItemsByOrderId(orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto findOrderItemsByOrderIdAndItemId(
            @PathVariable
            @Parameter(name = Constants.ID,
                description = OrderConstants.VALID_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long orderId,
            @PathVariable
            @Parameter(name = Constants.ID,
                description = OrderConstants.VALID_ITEM_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long itemId) {
        return orderService.findOrderItemsByOrderIdAndItemId(orderId, itemId);
    }
}
