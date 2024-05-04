package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.book.BookDto;
import com.example.onlinebookshop.dto.book.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(UpdateBookRequestDto requestDto, Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters);
}
