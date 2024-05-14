package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.dto.category.UpdateCategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto update(UpdateCategoryDto updateCategoryDto, Long id);

    void deleteById(Long id);
}
