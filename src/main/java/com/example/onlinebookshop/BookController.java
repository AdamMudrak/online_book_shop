package com.example.onlinebookshop;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import com.example.onlinebookshop.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto createBook(CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }
}
