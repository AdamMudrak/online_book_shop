package com.example.onlinebookshop.dto.order.response;

import com.example.onlinebookshop.dto.orderitem.OrderItemDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems = new HashSet<>();
    private LocalDateTime orderTime;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private StatusDto status;

    public enum StatusDto {
        CREATED,
        PENDING_PAYMENT,
        IN_PROGRESS,
        SHIPPED,
        COMPLETED,
        CANCELLED
    }
}

