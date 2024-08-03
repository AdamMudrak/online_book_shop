package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.cartitem.request.CreateCartItemDto;
import com.example.onlinebookshop.dtos.cartitem.request.UpdateCartItemDto;
import com.example.onlinebookshop.dtos.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartDto getShoppingCartByUserId(Long userId);

    ShoppingCartDto addBookToShoppingCart(Long userId,
                                          CreateCartItemDto createCartItemDto);

    ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                       UpdateCartItemDto quantity);

    void deleteBookFromShoppingCart(Long userId, Long cartItemId);
}
