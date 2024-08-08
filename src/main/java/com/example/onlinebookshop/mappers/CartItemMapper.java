package com.example.onlinebookshop.mappers;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.cartitem.request.CreateCartItemDto;
import com.example.onlinebookshop.dto.cartitem.response.CartItemDto;
import com.example.onlinebookshop.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    CartItem toCartItem(CreateCartItemDto createCartItemDto);
}
