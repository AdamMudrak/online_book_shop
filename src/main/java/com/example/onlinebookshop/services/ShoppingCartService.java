package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.request.UpdateQuantityInShoppingCartDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartByUserId(Long userId);

    ShoppingCartDto addBookToShoppingCart(Long userId,
                                          AddCartItemDto addCartItemDto);

    ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                       UpdateQuantityInShoppingCartDto quantity);

    void deleteBookFromShoppingCart(Long userId, Long cartItemId);
}
