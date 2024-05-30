package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.cartitem.request.CartItemRequestDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.User;

public interface ShoppingCartService {

    void createShoppingCart(User user);

    ShoppingCartDto getShoppingCartByUserId(Long userId);

    ShoppingCartDto addBookToShoppingCart(Long userId,
                                          CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                       UpdateItemQuantityDto quantity);

    void deleteBookFromShoppingCart(Long userId, Long cartItemId);
}
