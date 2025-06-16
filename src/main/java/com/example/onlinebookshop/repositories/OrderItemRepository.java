package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT orderitem FROM OrderItem orderitem "
            + "INNER JOIN orderitem.order order "
            + "INNER JOIN order.user user "
            + "WHERE orderitem.id = :id "
            + "AND order.id = :orderId "
            + "AND user.id = :userId")
    Optional<OrderItem> findByIdAndOrderIdAndUserId(Long id, Long orderId, Long userId);

    Optional<OrderItem> findByIdAndOrderId(Long id, Long orderId);
}
