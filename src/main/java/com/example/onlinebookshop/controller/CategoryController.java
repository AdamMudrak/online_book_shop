package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.controllerconstants.CategoryConstants;
import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.dto.category.request.CreateCategoryDto;
import com.example.onlinebookshop.dto.category.request.UpdateCategoryDto;
import com.example.onlinebookshop.dto.category.response.CategoryDto;
import com.example.onlinebookshop.services.BookService;
import com.example.onlinebookshop.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = CategoryConstants.CATEGORY_API_NAME,
        description = CategoryConstants.CATEGORY_API_DESCRIPTION)
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = CategoryConstants.GET_ALL_SUMMARY,
            description = CategoryConstants.GET_ALL_DESCRIPTION)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<CategoryDto> getAll(
            @Parameter(example = CategoryConstants.PAGEABLE_EXAMPLE) Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = CategoryConstants.GET_BY_ID_SUMMARY,
            description = CategoryConstants.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable @Parameter(name = Constants.ID,
            description = CategoryConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable @Parameter(name = Constants.ID,
            description = CategoryConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        return bookService.findAllWithoutCategoryIds(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = CategoryConstants.CREATE_CATEGORY_SUMMARY,
            description = CategoryConstants.CREATE_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_201,
                    description = Constants.SUCCESSFULLY_CREATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CreateCategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = CategoryConstants.UPDATE_CATEGORY_SUMMARY,
            description = CategoryConstants.UPDATE_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestBody @Valid UpdateCategoryDto categoryDto,
                                      @PathVariable @Parameter(name = Constants.ID,
                                              description = CategoryConstants.VALID_ID_DESCRIPTION,
                                              example = Constants.ID_EXAMPLE) @Positive Long id) {
        return categoryService.update(categoryDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = CategoryConstants.DELETE_CATEGORY_SUMMARY,
            description = CategoryConstants.DELETE_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_204,
                    description = Constants.CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Parameter(name = Constants.ID,
            description = CategoryConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        categoryService.deleteById(id);
    }
}
