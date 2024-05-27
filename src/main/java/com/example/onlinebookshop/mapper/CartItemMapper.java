package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.cartitem.response.CartItemDto;
import com.example.onlinebookshop.entities.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    CartItemDto toCartItemDto(CartItem cartItem);

    @AfterMapping
    default void setBookIdAndTitle(@MappingTarget CartItemDto cartItemDto,
                                   CartItem cartItem) {
        cartItemDto.setBookId(cartItem.getBook().getId());
        cartItemDto.setBookTitle(cartItem.getBook().getTitle());
    }
}
