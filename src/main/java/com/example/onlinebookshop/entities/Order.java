package com.example.onlinebookshop.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //TODO try and get from shopping_cart
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private BigDecimal total; //TODO in service, sum all prices * quantities
    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;
    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress; //TODO try and get from user
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    //TODO try and get from shopping_cart - transform cartItems
    @Column(nullable = false)
    private boolean isDeleted = false;

    public void addItemToOrder(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public enum Status {
        CREATED,
        PENDING_PAYMENT,
        IN_PROGRESS,
        SHIPPED,
        COMPLETED,
        CANCELLED
    }
}
