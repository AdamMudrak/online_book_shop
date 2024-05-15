package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.BookConstants;
import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.request.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookRequestDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.services.BookService;
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
@Tag(name = BookConstants.BOOK_API_NAME,
        description = BookConstants.BOOK_API_DESCRIPTION)
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = BookConstants.GET_ALL_SUMMARY,
            description = BookConstants.GET_ALL_DESCRIPTION)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<BookDto> getAll(
            @Parameter(example = BookConstants.PAGEABLE_EXAMPLE) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = BookConstants.GET_BY_ID_SUMMARY,
            description = BookConstants.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable @Parameter(name = Constants.ID,
            description = BookConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = BookConstants.SEARCH_BOOKS_SUMMARY,
            description = BookConstants.SEARCH_BOOKS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
    })
    @GetMapping("/search")
    public List<BookDto> searchBooks(@Valid BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = BookConstants.CREATE_BOOK_SUMMARY,
            description = BookConstants.CREATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_201,
                    description = Constants.SUCCESSFULLY_CREATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = BookConstants.UPDATE_BOOK_SUMMARY,
            description = BookConstants.UPDATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid UpdateBookRequestDto bookRequestDto,
                              @PathVariable @Parameter(
                                      name = Constants.ID,
                                      description = BookConstants.VALID_ID_DESCRIPTION,
                                      example = Constants.ID_EXAMPLE) @Positive Long id,
                             @Parameter(
                                     name = BookConstants.BOOLEAN,
                                     description = BookConstants.BOOLEAN_DESCRIPTION)
                                  boolean areCategoriesReplaced) {
        return bookService.update(bookRequestDto, id, areCategoriesReplaced);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = BookConstants.DELETE_BOOK_SUMMARY,
            description = BookConstants.DELETE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_204,
                    description = Constants.CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(name = Constants.ID,
            description = BookConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        bookService.delete(id);
    }
}
