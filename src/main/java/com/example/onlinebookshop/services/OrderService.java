package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.order.OrderStatusDto;
import com.example.onlinebookshop.dtos.order.request.CreateOrderDto;
import com.example.onlinebookshop.dtos.order.response.OrderDto;
import com.example.onlinebookshop.dtos.orderitem.response.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderDto> getOrders(Pageable pageable);

    List<OrderDto> getOrdersByUserId(Long userId, Pageable pageable);

    OrderDto addOrder(Long userId, CreateOrderDto createOrderDto);

    OrderDto updateOrderStatus(Long orderId, OrderStatusDto statusDto);

    List<OrderItemDto> findOrderItemsByOrderId(Long orderId);

    List<OrderItemDto> findOrderItemsByOrderId(Long userId, Long orderId);

    OrderItemDto findOrderItemsByOrderIdAndItemId(Long orderId, Long itemId);

    OrderItemDto findOrderItemsByOrderIdAndItemId(Long userId, Long orderId, Long itemId);
}
