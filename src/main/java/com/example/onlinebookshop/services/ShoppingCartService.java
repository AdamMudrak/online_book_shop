package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.request.UpdateQuantityInShoppingCartDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.dto.user.request.SendUserTokenRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartByUserId(SendUserTokenRequestDto token);

    ShoppingCartDto addBookToShoppingCart(SendUserTokenRequestDto token,
                                          AddCartItemDto addCartItemDto);

    ShoppingCartDto updateBookQuantity(SendUserTokenRequestDto token, Long cartItemId,
                                       UpdateQuantityInShoppingCartDto quantity);

    void deleteBookFromShoppingCart(SendUserTokenRequestDto token, Long cartItemId);
}
