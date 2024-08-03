package com.example.onlinebookshop.mappers;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dtos.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dtos.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dtos.category.response.CategoryDto;
import com.example.onlinebookshop.entities.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCreateModel(CreateCategoryDto categoryDto);

    Category toUpdateModel(UpdateCategoryDto categoryDto);
}
