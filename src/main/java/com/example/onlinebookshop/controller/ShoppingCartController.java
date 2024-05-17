package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.request.UpdateQuantityInShoppingCartDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.dto.user.request.SendUserTokenRequestDto;
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

    @GetMapping
    public ShoppingCartDto getShoppingCartByUserId(@RequestBody SendUserTokenRequestDto token) {
        return shoppingCartService.getShoppingCartByUserId(token);
    }

    @PostMapping
    public ShoppingCartDto addBookToShoppingCart(@RequestBody SendUserTokenRequestDto token,
                                                 @RequestBody AddCartItemDto addCartItemDto) {
        return shoppingCartService.addBookToShoppingCart(token, addCartItemDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(@RequestBody SendUserTokenRequestDto token,
                                              @PathVariable Long cartItemId,
                                              @RequestBody
                                              UpdateQuantityInShoppingCartDto quantity) {
        return shoppingCartService.updateBookQuantity(token, cartItemId, quantity);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteBookFromShoppingCart(@RequestBody SendUserTokenRequestDto token,
                                           @PathVariable Long cartItemId) {
        shoppingCartService.deleteBookFromShoppingCart(token, cartItemId);
    }
}
