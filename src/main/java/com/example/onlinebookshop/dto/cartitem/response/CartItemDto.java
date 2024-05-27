package com.example.onlinebookshop.dto.cartitem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    @JsonProperty("id")
    private Long outerId;
    @JsonProperty("bookId")
    private Long bookId;
    @JsonProperty("bookTitle")
    private String bookTitle;
    @JsonProperty("quantity")
    private int quantity;
}
