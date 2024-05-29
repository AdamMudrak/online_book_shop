package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.cartitem.response.CartItemDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setUserId(@MappingTarget ShoppingCartDto shoppingCartDto,
                           ShoppingCart shoppingCart) {
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
    }

    @AfterMapping
    default void setItems(@MappingTarget ShoppingCartDto shoppingCartDto,
                          ShoppingCart shoppingCart) {
        shoppingCart.getCartItems().forEach(cartItem -> {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setBookId(cartItem.getBook().getId());
            cartItemDto.setBookTitle(cartItem.getBook().getTitle());
            cartItemDto.setQuantity(cartItem.getQuantity());
            shoppingCartDto.getCartItems().add(cartItemDto);
        });
    }
}
