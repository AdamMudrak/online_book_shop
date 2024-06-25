package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.order.request.CreateOrderDto;
import com.example.onlinebookshop.dto.order.request.UpdateOrderDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.response.OrderItemDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto addOrder(Long userId, CreateOrderDto createOrderDto);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderDto updateOrderDto);

    List<OrderItemDto> findOrderItemsByOrderId(Long orderId);

    List<OrderItemDto> findOrderItemsByOrderId(Long userId, Long orderId);

    OrderItemDto findOrderItemsByOrderIdAndItemId(Long orderId, Long itemId);

    OrderItemDto findOrderItemsByOrderIdAndItemId(Long userId, Long orderId, Long itemId);
}
