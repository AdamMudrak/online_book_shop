package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.order.request.CreateOrderDto;
import com.example.onlinebookshop.dto.order.request.UpdateOrderDto;
import com.example.onlinebookshop.dto.order.response.OrderDto;
import com.example.onlinebookshop.dto.orderitem.response.OrderItemDto;
import com.example.onlinebookshop.entities.Order;
import com.example.onlinebookshop.entities.OrderItem;
import com.example.onlinebookshop.entities.ShoppingCart;
import com.example.onlinebookshop.exceptions.AddressNotFoundException;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
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
        order.setStatus(Order.Status.CREATED);
        setOrderItems(shoppingCart, order);
        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        setAddress(createOrderDto, shoppingCart, order);
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
                    o.setStatus(Status.valueOf(orderDto.status()));
                    return o;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found", orderId)
                ));
        return orderMapper.orderToOrderDto(order);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(getStatusByCode(updateOrderDto.status()));
            orderRepository.save(order);
            return orderMapper.orderToOrderDto(order);
        }
        throw new EntityNotFoundException("Can't find order with id " + orderId);
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return formOrderItemDtoList(order);
        }
        throw new EntityNotFoundException("Can't find order with id " + orderId);
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id: %d not found for user: %d", orderId, userId)
                ));
        return orderItemMapper.toOrderItemDtoList(order.getOrderItems());
        Order order = isUserLookingForHisOrders(optionalOrder, userId, orderId);
        return formOrderItemDtoList(order);
    }

    @Override
    public OrderItemDto findOrderItemsByOrderIdAndItemId(Long orderId, Long itemId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return getOrderItemById(order, itemId);
        }
        throw new EntityNotFoundException("Can't find order with id " + orderId);
    }

    @Override
    public OrderItemDto findOrderItemsByOrderIdAndItemId(Long userId, Long orderId, Long itemId) {
        OrderItem item = itemRepository.findByIdAndOrderIdAndUserId(orderItemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order item: %d not found in order: %d for user: %d",
                                orderItemId, orderId, userId)
                ));
        return orderItemMapper.toOrderItemDto(item);
        Order order = isUserLookingForHisOrders(optionalOrder, userId, orderId);
        return getOrderItemById(order, itemId);
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

    private void setAddress(CreateOrderDto createOrderDto, ShoppingCart shoppingCart, Order order) {
        if (createOrderDto.shippingAddress().isBlank()) {
            String shippingAddress = shoppingCart.getUser().getShippingAddress();
            if (!shippingAddress.isBlank()) {
                order.setShippingAddress(shippingAddress);
            } else {
                throw new AddressNotFoundException(
                        "Can't form an order with no address. Try again and be sure to input it.");
            }
        } else {
            order.setShippingAddress(createOrderDto.shippingAddress());
        }
    }

    private void setOrderTime(Order order) {
        LocalDateTime now = LocalDateTime.now();
        String nowWithoutMilliseconds = now.format(formatter);
        order.setOrderTime(LocalDateTime.parse(nowWithoutMilliseconds));
    }

    private Order.Status getStatusByCode(int statusCode) {
        return switch (statusCode) {
            case 1 -> Order.Status.CREATED;
            case 2 -> Order.Status.PENDING_PAYMENT;
            case 3 -> Order.Status.IN_PROGRESS;
            case 4 -> Order.Status.SHIPPED;
            case 5 -> Order.Status.COMPLETED;
            case 6 -> Order.Status.CANCELLED;
            default -> throw new EntityNotFoundException("Can't find status by code " + statusCode);
        };
    }

    private Order isUserLookingForHisOrders(
            Optional<Order> optionalOrder, Long userId, Long orderId) {
        if (optionalOrder.isPresent() && optionalOrder.get().getUser().getId().equals(userId)) {
            return optionalOrder.get();
        }
        throw new EntityNotFoundException("Can't find order with id " + orderId
                + " for user " + userId);
    }

    private List<OrderItemDto> formOrderItemDtoList(Order order) {
        return order.getOrderItems().stream()
                .map(orderItemMapper::toOrderItemDto)
                .toList();
    }

    private OrderItemDto getOrderItemById(Order order, Long itemId) {
        return order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .findFirst()
                .map(orderItemMapper::toOrderItemDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find item with id " + itemId));
    }
}
