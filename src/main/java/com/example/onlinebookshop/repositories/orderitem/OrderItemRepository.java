package com.example.onlinebookshop.repositories.orderitem;

import com.example.onlinebookshop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
