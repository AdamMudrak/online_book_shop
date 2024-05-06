package com.example.onlinebookshop.controller;

import static com.example.onlinebookshop.constants.BookConstants.BOOK_API_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.BOOK_API_NAME;
import static com.example.onlinebookshop.constants.BookConstants.CREATE_BOOK_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.CREATE_BOOK_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.DELETE_BOOK_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.DELETE_BOOK_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.GET_ALL_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.GET_ALL_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.GET_BY_ID_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.GET_BY_ID_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.PAGEABLE_EXAMPLE;
import static com.example.onlinebookshop.constants.BookConstants.SEARCH_BOOKS_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.SEARCH_BOOKS_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.UPDATE_BOOK_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookConstants.UPDATE_BOOK_SUMMARY;
import static com.example.onlinebookshop.constants.BookConstants.VALID_ID_DESCRIPTION;
import static com.example.onlinebookshop.constants.Constants.CODE_200;
import static com.example.onlinebookshop.constants.Constants.CODE_201;
import static com.example.onlinebookshop.constants.Constants.CODE_204;
import static com.example.onlinebookshop.constants.Constants.CODE_204_DESCRIPTION;
import static com.example.onlinebookshop.constants.Constants.CODE_400;
import static com.example.onlinebookshop.constants.Constants.ID;
import static com.example.onlinebookshop.constants.Constants.ID_EXAMPLE;
import static com.example.onlinebookshop.constants.Constants.INVALID_ENTITY_VALUE;
import static com.example.onlinebookshop.constants.Constants.INVALID_ID_DESCRIPTION;
import static com.example.onlinebookshop.constants.Constants.SUCCESSFULLY_CREATED;
import static com.example.onlinebookshop.constants.Constants.SUCCESSFULLY_RETRIEVED;
import static com.example.onlinebookshop.constants.Constants.SUCCESSFULLY_UPDATED;

import com.example.onlinebookshop.dto.book.BookDto;
import com.example.onlinebookshop.dto.book.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.UpdateBookRequestDto;
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
@Tag(name = BOOK_API_NAME,
        description = BOOK_API_DESCRIPTION)
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = GET_ALL_SUMMARY,
            description = GET_ALL_DESCRIPTION)
    @ApiResponse(responseCode = CODE_200, description = SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public List<BookDto> getAll(
            @Parameter(example = PAGEABLE_EXAMPLE) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = GET_BY_ID_SUMMARY,
            description = GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200,
                    description = SUCCESSFULLY_RETRIEVED),
            @ApiResponse(responseCode = CODE_400,
                    description = INVALID_ID_DESCRIPTION)
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable @Parameter(name = ID,
            description = VALID_ID_DESCRIPTION,
            example = ID_EXAMPLE) @Positive Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = SEARCH_BOOKS_SUMMARY,
            description = SEARCH_BOOKS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200,
                    description = SUCCESSFULLY_RETRIEVED),
    })
    @GetMapping("/search")
    public List<BookDto> searchBooks(@Valid BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

    @Operation(summary = CREATE_BOOK_SUMMARY,
            description = CREATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_201,
                    description = SUCCESSFULLY_CREATED),
            @ApiResponse(responseCode = CODE_400,
                    description = INVALID_ENTITY_VALUE)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @Operation(summary = UPDATE_BOOK_SUMMARY,
            description = UPDATE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200,
                    description = SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = CODE_400,
                    description = INVALID_ID_DESCRIPTION
                            + ". Or " + INVALID_ENTITY_VALUE)
    })
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid UpdateBookRequestDto bookRequestDto,
                              @PathVariable @Parameter(name = ID,
                                      description = VALID_ID_DESCRIPTION,
                                      example = ID_EXAMPLE) @Positive Long id) {
        return bookService.update(bookRequestDto, id);
    }

    @Operation(summary = DELETE_BOOK_SUMMARY,
            description = DELETE_BOOK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_204,
                    description = CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = CODE_400,
                    description = INVALID_ID_DESCRIPTION)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(name = ID,
            description = VALID_ID_DESCRIPTION,
            example = ID_EXAMPLE) @Positive Long id) {
        bookService.delete(id);
    }
}
