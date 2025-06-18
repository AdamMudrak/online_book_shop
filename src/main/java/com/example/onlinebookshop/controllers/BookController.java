package com.example.onlinebookshop.controllers;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllers.BookControllerConstants;
import com.example.onlinebookshop.dtos.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.request.UpdateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.example.onlinebookshop.exceptions.ConflictException;
import com.example.onlinebookshop.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = BookControllerConstants.BOOK_API_NAME,
        description = BookControllerConstants.BOOK_API_DESCRIPTION)
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = BookControllerConstants.GET_ALL_SUMMARY,
            description = BookControllerConstants.GET_ALL_DESCRIPTION)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<BookDto> getAll(
            @Parameter(example = BookControllerConstants.PAGEABLE_EXAMPLE) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = BookControllerConstants.GET_BY_ID_SUMMARY,
            description = BookControllerConstants.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable @Parameter(name = Constants.ID,
            description = BookControllerConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = BookControllerConstants.SEARCH_BOOKS_SUMMARY,
            description = BookControllerConstants.SEARCH_BOOKS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
    })
    @GetMapping("/search")
    public List<BookDto> searchBooks(@RequestParam(required = false) @Positive BigDecimal fromPrice,
                 @RequestParam(required = false) @Positive BigDecimal toPrice,
                 @RequestParam(required = false) String[] titles,
                 @RequestParam(required = false) String[] authors,
                 @Parameter(example = BookControllerConstants.PAGEABLE_EXAMPLE) Pageable pageable)
            throws ConflictException {
        return bookService.search(
                new BookSearchParametersDto(fromPrice, toPrice, titles, authors), pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = BookControllerConstants.CREATE_BOOK_SUMMARY,
            description = BookControllerConstants.CREATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_201,
                    description = Constants.SUCCESSFULLY_CREATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = BookControllerConstants.UPDATE_BOOK_SUMMARY,
            description = BookControllerConstants.UPDATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid UpdateBookDto bookRequestDto,
                              @PathVariable @Parameter(
                                      name = Constants.ID,
                                      description = BookControllerConstants.VALID_ID_DESCRIPTION,
                                      example = Constants.ID_EXAMPLE) @Positive Long id,
                              @Parameter(
                                     name = BookControllerConstants.BOOLEAN,
                                     description = BookControllerConstants.BOOLEAN_DESCRIPTION)
                                  boolean areCategoriesReplaced) {
        return bookService.update(bookRequestDto, id, areCategoriesReplaced);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = BookControllerConstants.DELETE_BOOK_SUMMARY,
            description = BookControllerConstants.DELETE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_204,
                    description = Constants.CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(name = Constants.ID,
            description = BookControllerConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        bookService.delete(id);
    }
}
