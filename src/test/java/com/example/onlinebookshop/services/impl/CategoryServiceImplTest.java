package com.example.onlinebookshop.services.impl;

import static com.example.onlinebookshop.BookCategoryConstants.ANOTHER_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.ANOTHER_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.DUPLICATE_OF_EXISTING_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.FIRST_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.NEW_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_PAGE_NUMBER;
import static com.example.onlinebookshop.BookCategoryConstants.UNLIMITED_PAGE_SIZE;
import static com.example.onlinebookshop.BookCategoryConstants.UPDATED_CATEGORY_DESCRIPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookshop.dtos.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dtos.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dtos.category.response.CategoryDto;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mappers.CategoryMapper;
import com.example.onlinebookshop.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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

    private static final Category NEW_CATEGORY = new Category();
    private static final Category EXISTING_CATEGORY = new Category();
    private static final Category EXISTING_CATEGORY_AFTER_UPDATE = new Category();
    private static final Category DUPLICATE_OF_EXISTING_CATEGORY = new Category();
    private static final CreateCategoryDto CREATE_CATEGORY_DTO =
            new CreateCategoryDto(FICTION_CATEGORY_NAME, FICTION_CATEGORY_DESCRIPTION);
    private static final CreateCategoryDto EXISTING_CREATE_CATEGORY_DTO =
            new CreateCategoryDto(ANOTHER_CATEGORY_NAME, ANOTHER_CATEGORY_DESCRIPTION);
    private static final UpdateCategoryDto UPDATE_CATEGORY_DTO = new UpdateCategoryDto(
            ANOTHER_CATEGORY_NAME,
            UPDATED_CATEGORY_DESCRIPTION);
    private static final CategoryDto NEW_CATEGORY_DTO = new CategoryDto(
            NEW_CATEGORY_ID,
            FICTION_CATEGORY_NAME,
            FICTION_CATEGORY_DESCRIPTION);
    private static final CategoryDto EXISTING_CATEGORY_DTO = new CategoryDto(
            FICTION_CATEGORY_ID,
            ANOTHER_CATEGORY_NAME,
            ANOTHER_CATEGORY_DESCRIPTION);
    private static final CategoryDto EXISTING_CATEGORY_DTO_AFTER_UPDATE = new CategoryDto(
            FICTION_CATEGORY_ID,
            ANOTHER_CATEGORY_NAME,
            UPDATED_CATEGORY_DESCRIPTION);
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    static void initVars() {
        NEW_CATEGORY.setId(NEW_CATEGORY_ID);
        NEW_CATEGORY.setName(FICTION_CATEGORY_NAME);
        NEW_CATEGORY.setDescription(FICTION_CATEGORY_DESCRIPTION);

        EXISTING_CATEGORY.setId(FICTION_CATEGORY_ID);
        EXISTING_CATEGORY.setName(ANOTHER_CATEGORY_NAME);
        EXISTING_CATEGORY.setDescription(ANOTHER_CATEGORY_DESCRIPTION);

        DUPLICATE_OF_EXISTING_CATEGORY.setId(DUPLICATE_OF_EXISTING_CATEGORY_ID);
        DUPLICATE_OF_EXISTING_CATEGORY.setName(ANOTHER_CATEGORY_NAME);
        DUPLICATE_OF_EXISTING_CATEGORY.setDescription(ANOTHER_CATEGORY_DESCRIPTION);

        EXISTING_CATEGORY_AFTER_UPDATE.setName(ANOTHER_CATEGORY_NAME);
        EXISTING_CATEGORY_AFTER_UPDATE.setDescription(UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @DisplayName("Given a CreateDto of a category which is not in DB, successfully saves it")
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
    @DisplayName("Given a CreateDto of a category which is already in DB by name, can't save it")
    void save_IsNotAbleToSaveCategoryWhichIsInDb_Fail() {
        String expectedMessage = "Category with name "
                + ANOTHER_CATEGORY_NAME + " already exists in DB";
        when(categoryRepository.existsByName(ANOTHER_CATEGORY_NAME))
                .thenThrow(new ParameterAlreadyExistsException(
                        "Category with name " + ANOTHER_CATEGORY_NAME + " already exists in DB"));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class,
                () -> categoryService.save(EXISTING_CREATE_CATEGORY_DTO));
        assertEquals(expectedMessage, exception.getMessage());

        verify(categoryRepository, times(1)).existsByName(ANOTHER_CATEGORY_NAME);
    }

    @Test
    @DisplayName("Given a pageable of first(zero) page and page sized 3, "
            + "successfully retrieves a list of 2 categories from DB")
    void findAll_IsAbleToFindTwoCategoriesFromDb_Success() {
        Pageable pageable = PageRequest.of(FIRST_PAGE_NUMBER, UNLIMITED_PAGE_SIZE);
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
    @DisplayName("Given a pageable of 1000th page and page sized 3, "
            + "retrieves an empty list from DB")
    void findAll_IsNotAbleToFindRandomCategory_Fail() {
        Pageable pageable = PageRequest.of(RANDOM_PAGE_NUMBER, UNLIMITED_PAGE_SIZE);
        List<Category> categories = List.of();
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, 0);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        List<CategoryDto> expectedCategoryDtos = List.of();
        List<CategoryDto> actualCategoryDtos = categoryService.findAll(pageable);

        assertEquals(expectedCategoryDtos, actualCategoryDtos);

        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Given a category id, successfully retrieves the category by id from DB")
    void getById_IsAbleToFindExistingCategory_Success() {
        when(categoryRepository.findById(FICTION_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryMapper.toCategoryDto(EXISTING_CATEGORY)).thenReturn(EXISTING_CATEGORY_DTO);
        CategoryDto byExistingCategoryId = categoryService.getById(FICTION_CATEGORY_ID);
        assertEquals(EXISTING_CATEGORY_DTO, byExistingCategoryId);

        verify(categoryRepository, times(1)).findById(FICTION_CATEGORY_ID);
        verify(categoryMapper, times(1)).toCategoryDto(EXISTING_CATEGORY);
    }

    @Test
    @DisplayName("Given a random category id, throws an exception")
    void getById_IsNotAbleToFindCategoryByRandomId_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_ID;
        when(categoryRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.getById(RANDOM_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_ID);
    }

    @Test
    @DisplayName("Given an UpdateDto, successfully updates the category in DB on condition that "
            + "it is already present by id")
    void update_CanUpdateCategoryWhenCategoryExistsById_Success() {
        when(categoryRepository.findById(FICTION_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryRepository.findByName(ANOTHER_CATEGORY_NAME))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        when(categoryMapper.toUpdateModel(UPDATE_CATEGORY_DTO))
                .thenReturn(EXISTING_CATEGORY_AFTER_UPDATE);
        when(categoryMapper.toCategoryDto(categoryRepository.save(EXISTING_CATEGORY_AFTER_UPDATE)))
                .thenReturn(EXISTING_CATEGORY_DTO_AFTER_UPDATE);
        CategoryDto actual = categoryService.update(UPDATE_CATEGORY_DTO, FICTION_CATEGORY_ID);
        assertEquals(EXISTING_CATEGORY_DTO_AFTER_UPDATE, actual);

        verify(categoryRepository, times(1)).findById(FICTION_CATEGORY_ID);
        verify(categoryRepository, times(1)).findByName(ANOTHER_CATEGORY_NAME);
        verify(categoryMapper, times(1)).toUpdateModel(UPDATE_CATEGORY_DTO);
        verify(categoryMapper, times(1))
                .toCategoryDto(categoryRepository.save(EXISTING_CATEGORY_AFTER_UPDATE));
    }

    @Test
    @DisplayName("Given an UpdateDto, throws an exception "
            + " as it is not present in DB by id")
    void update_CannotUpdateCategoryWhenCategoryDoesNotExistById_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_ID;
        when(categoryRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.update(UPDATE_CATEGORY_DTO, RANDOM_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_ID);
    }

    @Test
    @DisplayName("Throws an exception as another category with the same name is already present")
    void update_CannotUpdateCategoryWhenAnotherCategoryExistsByName_Fail() {
        String exceptionMessage = "Another category with name "
                + ANOTHER_CATEGORY_NAME + " already exists in DB";
        when(categoryRepository.findById(DUPLICATE_OF_EXISTING_CATEGORY_ID))
                .thenReturn(Optional.of(DUPLICATE_OF_EXISTING_CATEGORY));
        when(categoryRepository.findByName(ANOTHER_CATEGORY_NAME))
                .thenThrow(new ParameterAlreadyExistsException("Another category with name "
                        + ANOTHER_CATEGORY_NAME + " already exists in DB"));
        Exception exception = assertThrows(ParameterAlreadyExistsException.class, () ->
                categoryService.update(UPDATE_CATEGORY_DTO, DUPLICATE_OF_EXISTING_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(DUPLICATE_OF_EXISTING_CATEGORY_ID);
        verify(categoryRepository, times(1)).findByName(ANOTHER_CATEGORY_NAME);
    }

    @Test
    @DisplayName("Successfully deletes a category by id. For making sure the category is now gone, "
            + "when findById is engaged, throws an exception because category is not present "
            + "by id anymore")
    void deleteById_IsAbleToDeleteCategoryById_Success() {
        when(categoryRepository.findById(FICTION_CATEGORY_ID))
                .thenReturn(Optional.of(EXISTING_CATEGORY));
        categoryService.deleteById(FICTION_CATEGORY_ID);
        String exceptionMessage = "Can't find category by id " + FICTION_CATEGORY_ID;
        when(categoryRepository.findById(FICTION_CATEGORY_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + FICTION_CATEGORY_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.deleteById(FICTION_CATEGORY_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(2)).findById(FICTION_CATEGORY_ID);
        verify(categoryRepository, times(1)).deleteById(FICTION_CATEGORY_ID);
    }

    @Test
    @DisplayName("When findById is engaged, throws an exception because "
            + "category is not present by id")
    void deleteById_IsNotAbleToDeleteCategoryById_Fail() {
        String exceptionMessage = "Can't find category by id " + RANDOM_ID;
        when(categoryRepository.findById(RANDOM_ID))
                .thenThrow(new EntityNotFoundException(
                        "Can't find category by id " + RANDOM_ID));
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.deleteById(RANDOM_ID));
        assertEquals(exceptionMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(RANDOM_ID);
        verify(categoryRepository, times(0)).deleteById(RANDOM_ID);
    }
}
