package com.example.onlinebookshop.repositories.category;

import com.example.onlinebookshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
