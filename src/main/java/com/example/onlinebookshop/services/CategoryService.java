package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.dto.category.UpdateCategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto update(UpdateCategoryDto updateCategoryDto, Long id);

    void deleteById(Long id);
}
