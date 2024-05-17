package com.example.onlinebookshop.repositories.shoppingcart;

import com.example.onlinebookshop.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
