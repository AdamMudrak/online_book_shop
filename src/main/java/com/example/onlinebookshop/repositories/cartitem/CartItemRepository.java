package com.example.onlinebookshop.repositories.cartitem;

import com.example.onlinebookshop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
