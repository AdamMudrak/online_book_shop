package com.example.onlinebookshop.dto.orderitem;

public record OrderItemDto(
        Long id,
        Long order_id,
        Long book_id,
        int quantity){}
