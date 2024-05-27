package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.cartitem.response.CartItemDto;
import com.example.onlinebookshop.entities.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemDto toCartItemDto(CartItem cartItem);

    @AfterMapping
    default void setBookIdAndTitle(@MappingTarget CartItemDto cartItemDto,
                                   CartItem cartItem) {
        Long bookId = cartItem.getBook().getId();
        String bookTitle = cartItem.getBook().getTitle();
    }
}
