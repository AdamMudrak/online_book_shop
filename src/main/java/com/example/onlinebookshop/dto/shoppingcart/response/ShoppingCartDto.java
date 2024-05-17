package com.example.onlinebookshop.dto.shoppingcart.response;

import com.example.onlinebookshop.entities.CartItem;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems = new HashSet<>();
}
