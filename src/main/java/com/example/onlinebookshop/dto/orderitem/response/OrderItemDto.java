package com.example.onlinebookshop.dto.orderitem.response;

public record OrderItemDto(
        Long id,
        Long orderId,
        Long bookId,
        int quantity){}
