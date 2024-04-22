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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Books API",
        description = "Here you'll find a comprehensive overview of functions of this app.")
@RequestMapping(value = "/books")
public class BookController {
    private static final String SUCCESS_DESCRIPTION = "Successfully retrieved";
    private static final String VALID_ID_DESCRIPTION = "Book id, must exist in DB and"
            + " be greater than 0";
    private static final String INVALID_ID_DESCRIPTION = "Either a negative or non-existing id "
            + "provided. API will tell which one";
    private static final String INVALID_ENTITY_VALUE =
            "One of the parameters is invalid. API will show which one";
    private final BookService bookService;

    @Operation(summary = "Get all products optionally with pagination and sorting",
            description = "Returns all products if not provided with"
                    + " page number(page), page size(size) sort parameter(sort)")
    @ApiResponse(responseCode = "200", description = SUCCESS_DESCRIPTION)
    @GetMapping
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Get a book by id", description = "Returns a book as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SUCCESS_DESCRIPTION),
            @ApiResponse(responseCode = "400",
                    description = INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable @Parameter(name = "id",
            description = VALID_ID_DESCRIPTION, example = "1") Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Search book by params",
            description = "Returns a book with params sent if any")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SUCCESS_DESCRIPTION),
    })
    @GetMapping("/search")
    public List<BookDto> searchBooks(@Valid BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

    @Operation(summary = "Create a new book", description = "Creates a new book according to the "
            + "entity provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @Operation(summary = "Update an existing book",
            description = "Updates an existing book according to the entity provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400",
                    description = INVALID_ID_DESCRIPTION + ". Or " + INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid UpdateBookRequestDto bookRequestDto,
                              @PathVariable @Parameter(name = "id",
                                      description = VALID_ID_DESCRIPTION,
                                      example = "1") Long id) {
        return bookService.update(bookRequestDto, id);
    }

    @Operation(summary = "Delete an existing book",
            description = "Deletes an existing book according to the id provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400",
                    description = INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(name = "id", description = VALID_ID_DESCRIPTION,
            example = "1") Long id) {
        bookService.delete(id);
    }
}
