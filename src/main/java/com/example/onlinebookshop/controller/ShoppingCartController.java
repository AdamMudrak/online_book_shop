package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.request.UpdateQuantityInShoppingCartDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ShoppingCartDto getShoppingCartByUserId(@PathVariable Long userId) {
        return shoppingCartService.getShoppingCartByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ShoppingCartDto addBookToShoppingCart(@PathVariable Long userId,
                                                 @RequestBody AddCartItemDto addCartItemDto) {
        return shoppingCartService.addBookToShoppingCart(userId, addCartItemDto);
    }

    @PutMapping("/cart-items/{userId}/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(@PathVariable Long userId,
                                              @PathVariable Long cartItemId,
                                              @RequestBody
                                              UpdateQuantityInShoppingCartDto quantity) {
        return shoppingCartService.updateBookQuantity(userId, cartItemId, quantity);
    }

    @DeleteMapping("/cart-items/{userId}/{cartItemId}")
    public void deleteBookFromShoppingCart(@PathVariable Long userId,
                                           @PathVariable Long cartItemId) {
        shoppingCartService.deleteBookFromShoppingCart(userId, cartItemId);
    }
}
