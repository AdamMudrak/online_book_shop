package com.example.onlinebookshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dto.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dto.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dto.category.response.CategoryDto;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.CategoryMapper;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final long EXISTING_CATEGORY_ID = 1L;
    private static final long NEW_CATEGORY_ID = 2L;
    private static final long DUPLICATE_OF_EXISTING_CATEGORY_ID = 3L;
    private static final long RANDOM_CATEGORY_ID = 1000L;
    private static final String NEW_CATEGORY_NAME = "Fiction";
    private static final String NEW_CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";
    private static final String EXISTING_CATEGORY_NAME = "Horror";
    private static final String EXISTING_CATEGORY_DESCRIPTION = "Horror description";
    private static final String NEW_EXISTING_CATEGORY_DESCRIPTION = "NEW Horror description";
    private static final CreateCategoryDto CREATE_CATEGORY_DTO =
            new CreateCategoryDto(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
    private static final CreateCategoryDto EXISTING_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(EXISTING_CATEGORY_NAME, EXISTING_CATEGORY_DESCRIPTION);
    private static final Category NEW_CATEGORY = new Category();
    private static final Category EXISTING_CATEGORY = new Category();
    private static final Category EXISTING_CATEGORY_AFTER_UPDATE = new Category();
    private static final Category DUPLICATE_OF_EXISTING_CATEGORY = new Category();
    private static final UpdateCategoryDto UPDATE_CATEGORY_DTO = new UpdateCategoryDto(
            EXISTING_CATEGORY_NAME,
            NEW_EXISTING_CATEGORY_DESCRIPTION);
    private static final CategoryDto NEW_CATEGORY_DTO = new CategoryDto(
            NEW_CATEGORY_ID,
            NEW_CATEGORY_NAME,
            NEW_CATEGORY_DESCRIPTION);
    private static final CategoryDto EXISTING_CATEGORY_DTO = new CategoryDto(
            EXISTING_CATEGORY_ID,
            EXISTING_CATEGORY_NAME,
            EXISTING_CATEGORY_DESCRIPTION);
    private static final CategoryDto EXISTING_CATEGORY_DTO_AFTER_UPDATE = new CategoryDto(
            EXISTING_CATEGORY_ID,
            EXISTING_CATEGORY_NAME,
            NEW_EXISTING_CATEGORY_DESCRIPTION);
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 3;
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

        DUPLICATE_OF_EXISTING_CATEGORY.setId(DUPLICATE_OF_EXISTING_CATEGORY_ID);
        DUPLICATE_OF_EXISTING_CATEGORY.setName(EXISTING_CATEGORY_NAME);
        DUPLICATE_OF_EXISTING_CATEGORY.setDescription(EXISTING_CATEGORY_DESCRIPTION);

        EXISTING_CATEGORY_AFTER_UPDATE.setName(EXISTING_CATEGORY_NAME);
        EXISTING_CATEGORY_AFTER_UPDATE.setDescription(NEW_EXISTING_CATEGORY_DESCRIPTION);
    }

    @Test
    void save_IsAbleToSaveCategoryWhichIsNotInDb_Success() {
        when(categoryMapper.toCreateModel(CREATE_CATEGORY_DTO)).thenReturn(NEW_CATEGORY);
        when(categoryMapper.toCategoryDto(categoryRepository.save(NEW_CATEGORY)))
                .thenReturn(NEW_CATEGORY_DTO);
        CategoryDto actualCategoryDto = categoryService.save(CREATE_CATEGORY_DTO);
        assertEquals(NEW_CATEGORY_DTO, actualCategoryDto);

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
    void findAll_IsAbleToFindTwoCategoriesFromDb_Success() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Category> categories = List.of(NEW_CATEGORY, EXISTING_CATEGORY);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toCategoryDto(NEW_CATEGORY)).thenReturn(NEW_CATEGORY_DTO);
        when(categoryMapper.toCategoryDto(EXISTING_CATEGORY)).thenReturn(EXISTING_CATEGORY_DTO);

        List<CategoryDto> expectedCategoryDtos = List.of(NEW_CATEGORY_DTO, EXISTING_CATEGORY_DTO);
        List<CategoryDto> actualCategoryDtos = categoryService.findAll(pageable);

        assertEquals(expectedCategoryDtos, actualCategoryDtos);

        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryMapper, times(1)).toCategoryDto(NEW_CATEGORY);
        verify(categoryMapper, times(1)).toCategoryDto(EXISTING_CATEGORY);
    }

    @Test
    void findAll_IsNotAbleToFindRandomCategory_Fail() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Category> categories = List.of();
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, 0);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        List<CategoryDto> expectedCategoryDtos = List.of();
        List<CategoryDto> actualCategoryDtos = categoryService.findAll(pageable);

        assertEquals(expectedCategoryDtos, actualCategoryDtos);

        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void getById_IsAbleToFindExistingCategory_Success() {
        when(categoryRepository.findById(EXISTING_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryMapper.toCategoryDto(EXISTING_CATEGORY)).thenReturn(EXISTING_CATEGORY_DTO);
        CategoryDto byExistingCategoryId = categoryService.getById(EXISTING_CATEGORY_ID);
        assertEquals(EXISTING_CATEGORY_DTO, byExistingCategoryId);

        verify(categoryRepository, times(1)).findById(EXISTING_CATEGORY_ID);
        verify(categoryMapper, times(1)).toCategoryDto(EXISTING_CATEGORY);
    }

    @Test
    void getById_IsNotAbleToFindCategoryByRandomId_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_CATEGORY_ID;
        when(categoryRepository.findById(RANDOM_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.getById(RANDOM_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_CATEGORY_ID);
    }

    @Test
    void update_CanUpdateCategoryWhenCategoryExistsById_Success() {
        when(categoryRepository.findById(EXISTING_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryRepository.findByName(EXISTING_CATEGORY_NAME))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryMapper.toUpdateModel(UPDATE_CATEGORY_DTO))
                .thenReturn(EXISTING_CATEGORY_AFTER_UPDATE);
        when(categoryMapper.toCategoryDto(categoryRepository.save(EXISTING_CATEGORY_AFTER_UPDATE)))
                .thenReturn(EXISTING_CATEGORY_DTO_AFTER_UPDATE);
        CategoryDto actual = categoryService.update(UPDATE_CATEGORY_DTO, EXISTING_CATEGORY_ID);
        assertEquals(EXISTING_CATEGORY_DTO_AFTER_UPDATE, actual);

        verify(categoryRepository, times(1)).findById(EXISTING_CATEGORY_ID);
        verify(categoryRepository, times(1)).findByName(EXISTING_CATEGORY_NAME);
        verify(categoryMapper, times(1)).toUpdateModel(UPDATE_CATEGORY_DTO);
        verify(categoryMapper, times(1))
                .toCategoryDto(categoryRepository.save(EXISTING_CATEGORY_AFTER_UPDATE));
    }

    @Test
    void update_CannotUpdateCategoryWhenCategoryDoesNotExistById_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_CATEGORY_ID;
        when(categoryRepository.findById(RANDOM_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.update(UPDATE_CATEGORY_DTO, RANDOM_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_CATEGORY_ID);
    }

    @Test
    void update_CannotUpdateCategoryWhenAnotherCategoryExistsByName_Fail() {
        String exceptionMessage = "Another category with name "
                + EXISTING_CATEGORY_NAME + " already exists in DB";
        when(categoryRepository.findById(DUPLICATE_OF_EXISTING_CATEGORY_ID))
                .thenReturn(Optional.of(DUPLICATE_OF_EXISTING_CATEGORY));
        when(categoryRepository.findByName(EXISTING_CATEGORY_NAME))
                .thenThrow(new ParameterAlreadyExistsException("Another category with name "
                        + EXISTING_CATEGORY_NAME + " already exists in DB"));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class, () ->
                categoryService.update(UPDATE_CATEGORY_DTO, DUPLICATE_OF_EXISTING_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(DUPLICATE_OF_EXISTING_CATEGORY_ID);
        verify(categoryRepository, times(1)).findByName(EXISTING_CATEGORY_NAME);
    }

    @Test
    void deleteById_IsAbleToDeleteCategoryById_Success() {
        when(categoryRepository.findById(EXISTING_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        categoryService.deleteById(EXISTING_CATEGORY_ID);
        String exceptionMessage = "Can't find category by id " + EXISTING_CATEGORY_ID;
        when(categoryRepository.findById(EXISTING_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + EXISTING_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.deleteById(EXISTING_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(2)).findById(EXISTING_CATEGORY_ID);
        verify(categoryRepository, times(1)).deleteById(EXISTING_CATEGORY_ID);
    }

    @Test
    void deleteById_IsNotAbleToDeleteCategoryById_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_CATEGORY_ID;
        when(categoryRepository.findById(RANDOM_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.deleteById(RANDOM_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_CATEGORY_ID);
        verify(categoryRepository, times(0)).deleteById(RANDOM_CATEGORY_ID);
    }
}
