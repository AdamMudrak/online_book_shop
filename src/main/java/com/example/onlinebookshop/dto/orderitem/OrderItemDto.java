package com.example.onlinebookshop.dto.orderitem;

public record OrderItemDto(
        Long id,
        Long orderId,
        Long bookId,
        int quantity){}
