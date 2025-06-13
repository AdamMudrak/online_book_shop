package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Long> findIdByName(String name);

    boolean existsByName(String name);
}
