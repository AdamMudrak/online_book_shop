package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.order.OrderStatusDto;
import com.example.onlinebookshop.dtos.order.request.CreateOrderDto;
import com.example.onlinebookshop.dtos.order.response.OrderDto;
import com.example.onlinebookshop.dtos.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderDto> getOrders(User user, Pageable pageable);

    OrderDto addOrder(Long userId, CreateOrderDto createOrderDto);

    OrderDto updateOrderStatus(Long orderId, OrderStatusDto statusDto);

    List<OrderItemDto> findOrderItemsByOrderId(User user, Long orderId);

    OrderItemDto findOrderItemByOrderIdAndItemId(User user, Long orderId, Long itemId);
}
