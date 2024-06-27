package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.order.request.CreateOrderDto;
import com.example.onlinebookshop.dto.order.request.UpdateOrderDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.Order;
import com.example.onlinebookshop.entities.Order.Status;
import com.example.onlinebookshop.entities.OrderItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.OrderProcessingException;
import com.example.onlinebookshop.mapper.OrderItemMapper;
import com.example.onlinebookshop.mapper.OrderMapper;
import com.example.onlinebookshop.repositories.order.OrderRepository;
import com.example.onlinebookshop.repositories.orderitem.OrderItemRepository;
import com.example.onlinebookshop.repositories.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookshop.services.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderDto> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).stream()
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    @Transactional
    @Override
    public OrderDto addOrder(Long userId, CreateOrderDto createOrderDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            throw new EntityNotFoundException("Can't find shopping cart for user " + userId);
        }
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException(
                    "To post an order, you should have at least 1 product in your cart");
        }
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Status.CREATED);
        setOrderItems(shoppingCart, order);
        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        order.setShippingAddress(createOrderDto.shippingAddress());
        setOrderTime(order);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderDto updateOrderDto) {
        Order order = orderRepository.findById(orderId)
                .map(o -> {
                    o.setStatus(Status.valueOf(updateOrderDto.status()
                            .toUpperCase()
                            .trim()));
                    return o;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found", orderId)
                ));
        orderRepository.save(order);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found", orderId)
                ));
        return orderItemMapper.toOrderItemDtoList(order.getOrderItems());
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found for user: %d", orderId, userId)
                ));
        return orderItemMapper.toOrderItemDtoList(order.getOrderItems());
    }

    @Override
    public OrderItemDto findOrderItemsByOrderIdAndItemId(Long orderId, Long itemId) {
        OrderItem item = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order item: %d not found in order: %d",
                                itemId, orderId)
                ));
        return orderItemMapper.toOrderItemDto(item);
    }

    @Override
    public OrderItemDto findOrderItemsByOrderIdAndItemId(Long userId, Long orderId, Long itemId) {
        OrderItem item = orderItemRepository.findByIdAndOrderIdAndUserId(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order item: %d not found in order: %d for user: %d",
                                itemId, orderId, userId)
                ));
        return orderItemMapper.toOrderItemDto(item);
    }

    private void setOrderItems(ShoppingCart shoppingCart, Order order) {
        shoppingCart.getCartItems().forEach(
                cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(
                            cartItem.getBook().getPrice().multiply(
                                    BigDecimal.valueOf(cartItem.getQuantity())));
                    order.addItemToOrder(orderItem);
                });
    }

    private void setOrderTime(Order order) {
        LocalDateTime now = LocalDateTime.now();
        String nowWithoutMilliseconds = now.format(formatter);
        order.setOrderTime(LocalDateTime.parse(nowWithoutMilliseconds));
    }
}
