package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.dto.category.CreateCategoryDto;
import com.example.onlinebookshop.dto.category.UpdateCategoryDto;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.mapper.CategoryMapper;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import com.example.onlinebookshop.services.CategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryDto categoryDto) {
        Category category = categoryMapper.toCreateModel(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id " + id));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto update(UpdateCategoryDto categoryDto, Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category updatedCategory = categoryMapper.toUpdateModel(categoryDto);
            updatedCategory.setId(id);
            return categoryMapper.toCategoryDto(categoryRepository.save(updatedCategory));
        }
        throw new EntityNotFoundException("Can't find category by id " + id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Can't find category by id " + id);
    }
}
