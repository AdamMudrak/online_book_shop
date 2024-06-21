package com.example.onlinebookshop.repositories.order;

import com.example.onlinebookshop.entities.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findByUserId(Long userId);
}
