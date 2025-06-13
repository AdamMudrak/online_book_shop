package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dtos.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dtos.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dtos.category.response.CategoryDto;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mappers.CategoryMapper;
import com.example.onlinebookshop.repositories.CategoryRepository;
import com.example.onlinebookshop.services.CategoryService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.name())) {
            throw new ParameterAlreadyExistsException("Category with name " + categoryDto.name()
                    + " already exists in DB");
        }
        return categoryMapper.toCategoryDto(
                categoryRepository.save(
                        categoryMapper.toCreateModel(categoryDto)));
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toCategoryDto(
                categoryRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Can't find category by id " + id)));
    }

    @Override
    public CategoryDto update(UpdateCategoryDto categoryDto, Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category by id " + id);
        }
        Optional<Long> categoryIdByName = categoryRepository.findIdByName(categoryDto.name());
        if (categoryIdByName.isPresent()
                && !categoryIdByName.get().equals(id)) {
            throw new ParameterAlreadyExistsException("Another category with name "
                    + categoryDto.name() + " already exists in DB");
        }
        Category updatedCategory = categoryMapper.toUpdateModel(categoryDto);
        updatedCategory.setId(id);
        return categoryMapper.toCategoryDto(categoryRepository.save(updatedCategory));
    }

    @Override
    public void deleteById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Can't find category by id " + id);
        }
    }
}
