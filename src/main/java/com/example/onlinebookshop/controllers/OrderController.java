package com.example.onlinebookshop.controllers;

import static com.example.onlinebookshop.constants.Constants.STATUS_DTO_RULES;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllers.OrderControllerConstants;
import com.example.onlinebookshop.dtos.order.OrderStatusDto;
import com.example.onlinebookshop.dtos.order.request.CreateOrderDto;
import com.example.onlinebookshop.dtos.order.response.OrderDto;
import com.example.onlinebookshop.dtos.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.Role;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = OrderControllerConstants.ORDER_API_NAME,
        description = OrderControllerConstants.ORDER_API_DESCRIPTION)
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = OrderControllerConstants.GET_ALL_SUMMARY)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<OrderDto> getOrdersByUserId(@AuthenticationPrincipal User user,
                    @Parameter(example = OrderControllerConstants.PAGEABLE_EXAMPLE)
                    Pageable pageable) {
        if (userIsAdmin(user)) {
            return orderService.getOrders(pageable);
        }
        return orderService.getOrdersByUserId(user.getId(), pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = OrderControllerConstants.ADD_ORDER_SUMMARY,
            description = OrderControllerConstants.ADD_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_201,
                    description = Constants.SUCCESSFULLY_CREATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@AuthenticationPrincipal User user,
                             @Valid
                             @RequestBody
                             CreateOrderDto createOrderDto) {
        return orderService.addOrder(user.getId(), createOrderDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = OrderControllerConstants.UPDATE_ORDER_SUMMARY,
            description = STATUS_DTO_RULES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PatchMapping("/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @Valid
                                      @RequestParam OrderStatusDto statusDto) {
        return orderService.updateOrderStatus(orderId, statusDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = OrderControllerConstants.GET_ORDER_ITEMS_BY_ORDER_ID)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })

    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> findOrderItemsByOrderId(
            @AuthenticationPrincipal User user,
            @PathVariable
            @Parameter(name = OrderControllerConstants.ORDER_ID,
                description = OrderControllerConstants.VALID_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long orderId) {
        if (userIsAdmin(user)) {
            return orderService.findOrderItemsByOrderId(orderId);
        }
        return orderService.findOrderItemsByOrderId(user.getId(), orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = OrderControllerConstants.GET_ORDER_ITEM_BY_ORDER_ID_AND_ITEM_ID)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto findOrderItemsByOrderIdAndItemId(
            @AuthenticationPrincipal User user,
            @PathVariable
            @Parameter(name = OrderControllerConstants.ORDER_ID,
                description = OrderControllerConstants.VALID_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long orderId,
            @PathVariable
            @Parameter(name = OrderControllerConstants.ITEM_ID,
                description = OrderControllerConstants.VALID_ITEM_ID_DESCRIPTION,
                example = Constants.ID_EXAMPLE) @Positive Long itemId) {
        if (userIsAdmin(user)) {
            return orderService.findOrderItemsByOrderIdAndItemId(orderId, itemId);
        }
        return orderService.findOrderItemsByOrderIdAndItemId(user.getId(), orderId, itemId);
    }

    private boolean userIsAdmin(User user) {
        return user.getRole().getName().equals(Role.RoleName.ROLE_ADMIN);
    }
}
