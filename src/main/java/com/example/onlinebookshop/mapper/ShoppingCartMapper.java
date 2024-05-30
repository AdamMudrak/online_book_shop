package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);
}
