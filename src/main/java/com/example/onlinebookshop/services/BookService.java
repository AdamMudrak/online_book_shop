package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
