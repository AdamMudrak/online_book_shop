package com.example.onlinebookshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dto.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dto.category.response.CategoryDto;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.CategoryMapper;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final long EXISTING_CATEGORY_ID = 1L;
    private static final long NEW_CATEGORY_ID = 2L;
    private static final String NEW_CATEGORY_NAME = "Fiction";
    private static final String NEW_CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";
    private static final String EXISTING_CATEGORY_NAME = "Horror";
    private static final String EXISTING_CATEGORY_DESCRIPTION = "Horror description";
    private static final CreateCategoryDto CREATE_CATEGORY_DTO =
            new CreateCategoryDto(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
    private static final CreateCategoryDto EXISTING_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(EXISTING_CATEGORY_NAME, EXISTING_CATEGORY_DESCRIPTION);
    private static final Category NEW_CATEGORY = new Category();
    private static final Category EXISTING_CATEGORY = new Category();
    private static final CategoryDto CATEGORY_DTO = new CategoryDto(
            NEW_CATEGORY_ID,
            NEW_CATEGORY_NAME,
            NEW_CATEGORY_DESCRIPTION);
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    static void initVars() {
        NEW_CATEGORY.setId(NEW_CATEGORY_ID);
        NEW_CATEGORY.setName(NEW_CATEGORY_NAME);
        NEW_CATEGORY.setDescription(NEW_CATEGORY_DESCRIPTION);

        EXISTING_CATEGORY.setId(EXISTING_CATEGORY_ID);
        EXISTING_CATEGORY.setName(EXISTING_CATEGORY_NAME);
        EXISTING_CATEGORY.setDescription(EXISTING_CATEGORY_DESCRIPTION);
    }

    @Test
    void save_IsAbleToSaveCategoryWhichIsNotInDb_Success() {
        when(categoryMapper.toCreateModel(CREATE_CATEGORY_DTO)).thenReturn(NEW_CATEGORY);
        when(categoryMapper.toCategoryDto(categoryRepository.save(NEW_CATEGORY)))
                .thenReturn(CATEGORY_DTO);
        CategoryDto actualCategoryDto = categoryService.save(CREATE_CATEGORY_DTO);
        assertEquals(CATEGORY_DTO, actualCategoryDto);

        verify(categoryMapper, times(1)).toCreateModel(CREATE_CATEGORY_DTO);
        verify(categoryMapper, times(1)).toCategoryDto(categoryRepository.save(NEW_CATEGORY));

    }

    @Test
    void save_IsNotAbleToSaveCategoryWhichIsInDb_Fail() {
        String expectedMessage = "Category with name "
                + EXISTING_CATEGORY_NAME + " already exists in DB";
        when(categoryRepository.existsByName(EXISTING_CATEGORY_NAME))
                .thenThrow(new ParameterAlreadyExistsException(
                        "Category with name " + EXISTING_CATEGORY_NAME + " already exists in DB"));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class,
                () -> categoryService.save(EXISTING_CREATE_CATEGORY_DTO));
        assertEquals(expectedMessage, exception.getMessage());

        verify(categoryRepository, times(1)).existsByName(EXISTING_CATEGORY_NAME);
    }

    @Test
    void findAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}
