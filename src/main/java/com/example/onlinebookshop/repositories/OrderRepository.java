package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    List<Order> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
