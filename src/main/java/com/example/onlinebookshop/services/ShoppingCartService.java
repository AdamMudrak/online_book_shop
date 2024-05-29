package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.User;

public interface ShoppingCartService {

    void createShoppingCart(User user);

    ShoppingCartDto getShoppingCartByUserEmail(String email);

    ShoppingCartDto addBookToShoppingCart(String email,
                                          AddCartItemDto addCartItemDto);

    ShoppingCartDto updateBookQuantity(String email, Long cartItemId,
                                       UpdateItemQuantityDto quantity);

    void deleteBookFromShoppingCart(String email, Long cartItemId);
}
