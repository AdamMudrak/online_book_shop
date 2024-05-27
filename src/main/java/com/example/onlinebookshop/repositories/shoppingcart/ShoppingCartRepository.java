package com.example.onlinebookshop.repositories.shoppingcart;

import com.example.onlinebookshop.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query(value = "SELECT cart FROM ShoppingCart cart "
            + "INNER JOIN cart.user user "
            + "WHERE user.email = :email")
    ShoppingCart findByUserEmail(String email);
}
