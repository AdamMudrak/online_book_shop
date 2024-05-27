package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.request.UpdateQuantityInShoppingCartDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartByUserEmail(String email);

    ShoppingCartDto addBookToShoppingCart(String email,
                                          AddCartItemDto addCartItemDto);

    ShoppingCartDto updateBookQuantity(String email, Long cartItemId,
                                       UpdateQuantityInShoppingCartDto quantity);

    void deleteBookFromShoppingCart(String email, Long cartItemId);
}
