package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.CartItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ObjectAlreadyExistsException;
import com.example.onlinebookshop.mapper.CartItemMapper;
import com.example.onlinebookshop.mapper.ShoppingCartMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.cartitem.CartItemRepository;
import com.example.onlinebookshop.repositories.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookshop.services.ShoppingCartService;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public ShoppingCartDto getShoppingCartByUserEmail(String email) {
        return cartMapper.toDto(repository.findByUserId(userId));
        ShoppingCartDto shoppingCartDto =
                shoppingCartMapper.toShoppingCartDto(shoppingCartRepository.findByUserEmail(email));
        setCartItemsInDto(shoppingCartDto, shoppingCart);
        return shoppingCartDto;
    }

    @Override
    public ShoppingCartDto addBookToShoppingCart(String email,
                                                 AddCartItemDto addCartItemDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        Long bookId = addCartItemDto.bookId();
        checkIfCartContainsSameItem(shoppingCart, bookId);
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + bookId)));
        cartItem.setQuantity(addCartItemDto.quantity());
        setOuterId(shoppingCart, cartItem);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toShoppingCartDto(shoppingCart);
        setCartItemsInDto(shoppingCartDto, shoppingCart);
        return shoppingCartDto;
    }

    @Override
    public ShoppingCartDto updateBookQuantity(String email, Long cartItemId,
                                              UpdateItemQuantityDto quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        CartItem cartItem = itemRepository.findByIdAndShoppingCartId(cartItemId, cart.getId())
                .map(item -> {
                    item.setQuantity(itemDto.quantity());
                    return item;
                }).orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cart item with id: %d for user: %d", cartItemId, userId)
                ));
                itemRepository.save(cartItem);
               return cartMapper.toDto(cart);
        cartItem.setQuantity(quantity.quantity());
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toShoppingCartDto(shoppingCart);
        setCartItemsInDto(shoppingCartDto, shoppingCart);
        return shoppingCartDto;
    }

    @Override
    public void deleteBookFromShoppingCart(String email, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        CartItem cartItem = itemRepository.findByIdAndShoppingCartId(itemId, shoppingCart.getId()).....
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private CartItem getCartItemById(ShoppingCart shoppingCart, Long cartItemId) {
        return shoppingCart.getCartItems().stream()
                .filter(cartItemInCart -> cartItemInCart.getOuterId().equals(cartItemId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(
                        "CartItem with id " + cartItemId + " not found"));
    }

    private void setCartItemsInDto(ShoppingCartDto shoppingCartDto, ShoppingCart shoppingCart) {
        shoppingCartDto.setCartItems(shoppingCart.getCartItems()
                .stream()
                .map(cartItemMapper::toCartItemDto)
                .collect(Collectors.toSet()));
    }

    private void setOuterId(ShoppingCart shoppingCart, CartItem cartItem) {
        if (!shoppingCart.getCartItems().isEmpty()) {
            cartItem.setOuterId(shoppingCart.getCartItems()
                    .stream()
                    .map(CartItem::getOuterId).max(Comparator.naturalOrder()).get() + 1);
        } else {
            cartItem.setOuterId(1L);
        }
    }

    private void checkIfCartContainsSameItem(ShoppingCart shoppingCart, Long bookId) {
        Optional<CartItem> cartItemWithTheSameId = shoppingCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(bookId))
                .findFirst();
        if (cartItemWithTheSameId.isPresent()) {
            throw new ObjectAlreadyExistsException(
                    "CartItem with id " + bookId + " already exists");
        }
    }
}
