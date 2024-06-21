package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.order.request.AddressDto;
import com.example.onlinebookshop.dto.order.request.StatusRequestDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.OrderItemDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto addOrder(Long userId, AddressDto addressDto);

    OrderDto updateOrderStatus(Long orderId, StatusRequestDto statusRequestDto);

    OrderDto findOrderItemsByOrderId(Long orderId);

    OrderItemDto findOrderItemsByOrderIdAndItemId(Long orderId, Long itemId);
}
