package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.dto.category.UpdateCategoryDto;
import com.example.onlinebookshop.entities.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toModel(CategoryDto categoryDto);

    Category toUpdateModel(UpdateCategoryDto categoryDto);
}
