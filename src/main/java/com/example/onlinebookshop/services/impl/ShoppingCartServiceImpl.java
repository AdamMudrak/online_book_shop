package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dtos.cartitem.request.CreateCartItemDto;
import com.example.onlinebookshop.dtos.cartitem.request.UpdateCartItemDto;
import com.example.onlinebookshop.dtos.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.CartItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.TooManyObjectsException;
import com.example.onlinebookshop.mappers.CartItemMapper;
import com.example.onlinebookshop.mappers.ShoppingCartMapper;
import com.example.onlinebookshop.repositories.BookRepository;
import com.example.onlinebookshop.repositories.CartItemRepository;
import com.example.onlinebookshop.repositories.ShoppingCartRepository;
import com.example.onlinebookshop.services.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCartByUserId(Long userId) {
        return shoppingCartMapper
                .toShoppingCartDto(shoppingCartRepository.findByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException("Can't find shopping cart for user "
                                + userId + " with id " + userId + ".")));
    }

    @Override
    public ShoppingCartDto addBookToShoppingCart(Long userId,
                                                 CreateCartItemDto createCartItemDto) {
        Book book = bookRepository.findById(createCartItemDto.bookId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + createCartItemDto.bookId()
                        + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart for user "
                        + userId + " with id " + userId + "."));
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(createCartItemDto.bookId()))
                .findFirst()
                .ifPresentOrElse(item -> {
                    if (item.getQuantity() + createCartItemDto.quantity() <= 100) {
                        item.setQuantity(item.getQuantity() + createCartItemDto.quantity());
                    } else {
                        throw new TooManyObjectsException("Quantity is limited to a 100 "
                                + "items per client. For more items, please contact "
                                + "us by phone or email.");
                    }
                },() -> addCartItemToCart(createCartItemDto, book, shoppingCart));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId,
                                              UpdateCartItemDto itemQuantityDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart for user "
                        + userId + " with id " + userId + "."));
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
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart for user "
                        + userId + " with id " + userId + "."));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId()).orElseThrow(() ->
                new EntityNotFoundException(("Cart item with id " + cartItemId + " not found")));
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private void addCartItemToCart(CreateCartItemDto createCartItemDto, Book book,
                                   ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toCartItem(createCartItemDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItemRepository.save(cartItem);
        shoppingCart.addItemToCart(cartItem);
    }
}
