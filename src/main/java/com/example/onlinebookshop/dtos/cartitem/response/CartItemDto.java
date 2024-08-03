package com.example.onlinebookshop.dtos.cartitem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartItemDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("bookId")
    private Long bookId;
    @JsonProperty("bookTitle")
    private String bookTitle;
    @JsonProperty("quantity")
    private int quantity;
}
