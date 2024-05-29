package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.CartItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.mapper.CartItemMapper;
import com.example.onlinebookshop.mapper.ShoppingCartMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.cartitem.CartItemRepository;
import com.example.onlinebookshop.repositories.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookshop.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCartByUserEmail(Long userId) {
        return shoppingCartMapper.toShoppingCartDto(shoppingCartRepository.findByUserId(userId));
    }

    @Override
    public ShoppingCartDto addBookToShoppingCart(Long userId,
                                                 AddCartItemDto addCartItemDto) {
        Book book = bookRepository.findById(addCartItemDto.bookId()).orElseThrow(() ->
            new EntityNotFoundException("Book with id " + addCartItemDto.bookId() + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(addCartItemDto.bookId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(
                        item.getQuantity() + addCartItemDto.quantity()),
                        () -> addCartItemToCart(addCartItemDto, book, shoppingCart));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                              UpdateItemQuantityDto itemQuantityDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId())
                .map(item -> {
                    item.setQuantity(itemQuantityDto.quantity());
                    return item;
                }).orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cart item with id: %d for user: %d", cartItemId, userId)
                ));
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public void deleteBookFromShoppingCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId()).orElseThrow(() ->
                new EntityNotFoundException(("Cart item with id " + cartItemId + " not found")));
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private void addCartItemToCart(AddCartItemDto addCartItemDto, Book book, ShoppingCart cart) {

    }
}
