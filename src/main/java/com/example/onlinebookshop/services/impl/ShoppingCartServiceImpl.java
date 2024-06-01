package com.example.onlinebookshop.services.impl;

import static com.example.onlinebookshop.constants.dtoconstants.CartItemDtoConstants.QUANTITY_DESCRIPTION;

import com.example.onlinebookshop.dto.cartitem.request.CartItemRequestDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.CartItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.TooManyObjectsException;
import com.example.onlinebookshop.mapper.CartItemMapper;
import com.example.onlinebookshop.mapper.ShoppingCartMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.cartitem.CartItemRepository;
import com.example.onlinebookshop.repositories.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookshop.services.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
        return shoppingCartMapper.toShoppingCartDto(shoppingCartRepository.findByUserId(userId));
    }

    @Transactional
    @Override
    public ShoppingCartDto addBookToShoppingCart(Long userId,
                                                 CartItemRequestDto cartItemRequestDto) {
        Book book = bookRepository.findById(cartItemRequestDto.bookId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + cartItemRequestDto.bookId()
                        + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(cartItemRequestDto.bookId()))
                .findFirst()
                .ifPresentOrElse(item -> {
                    if (item.getQuantity() + cartItemRequestDto.quantity() <= 100) {
                        item.setQuantity(item.getQuantity() + cartItemRequestDto.quantity());
                    } else {
                        throw new TooManyObjectsException(QUANTITY_DESCRIPTION);
                    }
                },() -> addCartItemToCart(cartItemRequestDto, book, shoppingCart));
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

    @Transactional
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

    private void addCartItemToCart(CartItemRequestDto cartItemRequestDto, Book book,
                                   ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItemRepository.save(cartItem);
        shoppingCart.addItemToCart(cartItem);
    }
}
