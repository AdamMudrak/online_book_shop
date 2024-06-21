package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.dto.order.request.AddressDto;
import com.example.onlinebookshop.dto.order.request.StatusRequestDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.OrderItemDto;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.services.OrderService;
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
                             @RequestBody AddressDto addressDto) {
        return orderService.addOrder(user.getId(), addressDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @RequestBody StatusRequestDto statusRequestDto) {
        return orderService.updateOrderStatus(orderId, statusRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> findOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderService.findOrderItemsByOrderId(orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto findOrderItemsByOrderIdAndItemId(@PathVariable Long orderId,
                                                         @PathVariable Long itemId) {
        return orderService.findOrderItemsByOrderIdAndItemId(orderId, itemId);
    }
}
