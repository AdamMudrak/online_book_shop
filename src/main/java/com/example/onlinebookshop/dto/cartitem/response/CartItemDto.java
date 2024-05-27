package com.example.onlinebookshop.dto.cartitem.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long outerId;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
