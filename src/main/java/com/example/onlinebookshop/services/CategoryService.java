package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dtos.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dtos.category.response.CategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryDto categoryDto);

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto update(UpdateCategoryDto updateCategoryDto, Long id);

    void deleteById(Long id);
}
