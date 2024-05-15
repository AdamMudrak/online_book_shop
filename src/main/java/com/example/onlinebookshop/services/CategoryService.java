package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.dto.category.CreateCategoryDto;
import com.example.onlinebookshop.dto.category.UpdateCategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryDto categoryDto);

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto update(UpdateCategoryDto updateCategoryDto, Long id);

    void deleteById(Long id);
}
