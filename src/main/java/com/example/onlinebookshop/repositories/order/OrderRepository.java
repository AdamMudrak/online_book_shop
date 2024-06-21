package com.example.onlinebookshop.repositories.order;

import com.example.onlinebookshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
