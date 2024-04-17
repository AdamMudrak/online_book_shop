package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.BookSearchParametersDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto update(CreateBookRequestDto requestDto, Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters);
}
