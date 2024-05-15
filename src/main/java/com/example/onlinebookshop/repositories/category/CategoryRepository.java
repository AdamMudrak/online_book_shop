package com.example.onlinebookshop.repositories.category;

import com.example.onlinebookshop.entities.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
