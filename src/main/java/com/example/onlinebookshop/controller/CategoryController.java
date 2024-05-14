package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.CategoryConstants;
import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = CategoryConstants.CATEGORY_API_NAME,
        description = CategoryConstants.CATEGORY_API_DESCRIPTION)
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        return null;
    }

    public List getAll() {
        return null;
    }

    public CategoryDto getCategoryById(Long id) {
        return null;
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return null;
    }

    public void deleteCategory(Long id) {

    }

    //(endpoint: "/{id}/books")
    public List getBooksByCategoryId(Long id)  {
        return null;
    }
}
