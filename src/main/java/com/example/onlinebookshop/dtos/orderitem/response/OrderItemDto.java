package com.example.onlinebookshop.dtos.orderitem.response;

public record OrderItemDto(
        Long id,
        Long orderId,
        Long bookId,
        int quantity){}
