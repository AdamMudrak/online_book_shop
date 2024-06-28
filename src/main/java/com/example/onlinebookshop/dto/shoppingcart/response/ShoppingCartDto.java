package com.example.onlinebookshop.dto.shoppingcart.response;

import com.example.onlinebookshop.dto.cartitem.response.CartItemDto;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems = new HashSet<>();
}
