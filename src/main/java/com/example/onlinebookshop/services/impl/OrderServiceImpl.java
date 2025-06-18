package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dtos.order.OrderStatusDto;
import com.example.onlinebookshop.dtos.order.request.CreateOrderDto;
import com.example.onlinebookshop.dtos.order.response.OrderDto;
import com.example.onlinebookshop.dtos.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.Order;
import com.example.onlinebookshop.entities.Order.Status;
import com.example.onlinebookshop.entities.OrderItem;
import com.example.onlinebookshop.entities.Role;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.OrderProcessingException;
import com.example.onlinebookshop.mappers.OrderItemMapper;
import com.example.onlinebookshop.mappers.OrderMapper;
import com.example.onlinebookshop.repositories.OrderItemRepository;
import com.example.onlinebookshop.repositories.OrderRepository;
import com.example.onlinebookshop.repositories.ShoppingCartRepository;
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
@Transactional
public class OrderServiceImpl implements OrderService {
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderDto> getOrders(User user, Pageable pageable) {
        if (userIsAdmin(user)) {
            return orderMapper.ordersToOrderDtos(orderRepository.findAll(pageable).getContent());
        } else {
            return orderMapper.ordersToOrderDtos(orderRepository
                    .findAllByUserId(user.getId(), pageable));
        }
    }

    @Override
    public OrderDto addOrder(Long userId, CreateOrderDto createOrderDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart for user " + userId));
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
    public OrderDto updateOrderStatus(Long orderId, OrderStatusDto statusDto) {
        Order orderById = orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(
                            Order.Status.valueOf(statusDto.name()));
                    return order;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found", orderId)
                ));
        orderRepository.save(orderById);
        return orderMapper.orderToOrderDto(orderById);
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(User user, Long orderId) {
        if (userIsAdmin(user)) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with id: %d not found", orderId)
                    ));
            return orderItemMapper.toOrderItemDtoList(order.getOrderItems());
        } else {
            Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with id: %d not found for user: %d",
                                    orderId, user.getId())));
            return orderItemMapper.toOrderItemDtoList(order.getOrderItems());
        }
    }

    @Override
    public OrderItemDto findOrderItemByOrderIdAndItemId(User user, Long orderId, Long itemId) {
        if (userIsAdmin(user)) {
            OrderItem item = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order item: %d not found in order: %d",
                                    itemId, orderId)
                    ));
            return orderItemMapper.toOrderItemDto(item);
        } else {
            OrderItem item = orderItemRepository.findByIdAndOrderIdAndUserId(
                    itemId, orderId, user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order item: %d not found in order: %d for user: %d",
                                    itemId, orderId, user.getId())
                    ));
            return orderItemMapper.toOrderItemDto(item);
        }
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

    private boolean userIsAdmin(User user) {
        return user.getRole().getName().equals(Role.RoleName.ROLE_ADMIN);
    }
}
