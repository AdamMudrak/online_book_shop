package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.BookSearchParametersDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import com.example.onlinebookshop.dto.UpdateBookRequestDto;
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
@Tag(name = Constants.BOOK_API_NAME,
        description = Constants.BOOK_API_DESCRIPTION)
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = Constants.GET_ALL_SUMMARY,
            description = Constants.GET_ALL_DESCRIPTION)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<BookDto> getAll(
            @Parameter(example = Constants.PAGEABLE_EXAMPLE) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = Constants.GET_BY_ID_SUMMARY, description = Constants.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable @Parameter(name = Constants.ID,
            description = Constants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = Constants.SEARCH_BOOKS_SUMMARY,
            description = Constants.SEARCH_BOOKS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_RETRIEVED),
    })
    @GetMapping("/search")
    public List<BookDto> searchBooks(@Valid BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

    @Operation(summary = Constants.CREATE_BOOK_SUMMARY,
            description = Constants.CREATE_BOOK_DESCRIPTION)
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

    @Operation(summary = Constants.UPDATE_BOOK_SUMMARY,
            description = Constants.UPDATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid UpdateBookRequestDto bookRequestDto,
                              @PathVariable @Parameter(name = Constants.ID,
                                      description = Constants.VALID_ID_DESCRIPTION,
                                      example = Constants.ID_EXAMPLE) @Positive Long id) {
        return bookService.update(bookRequestDto, id);
    }

    @Operation(summary = Constants.DELETE_BOOK_SUMMARY,
            description = Constants.DELETE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_204,
                    description = Constants.CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(name = Constants.ID,
            description = Constants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE) @Positive Long id) {
        bookService.delete(id);
    }
}
