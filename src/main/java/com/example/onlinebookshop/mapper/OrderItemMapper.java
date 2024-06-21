package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.orderitem.OrderItemDto;
import com.example.onlinebookshop.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
