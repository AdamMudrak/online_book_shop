package com.example.onlinebookshop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @Column(nullable = false)
    @ManyToOne
    @JoinTable(
            name = "cart_items_books",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Book book;
    @Column(nullable = false)
    private int quantity;
}
