package com.example.onlinebookshop.dto.cartitem.response;

public record CartItemDto(
        Long outerId,
        Long bookId,
        String bookTitle,
        int quantity) {
}
