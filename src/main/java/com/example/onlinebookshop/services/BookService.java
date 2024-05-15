package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.book.BookDto;
import com.example.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.dto.book.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllWithoutCategoryIds(Long id);

    BookDto findById(Long id);

    BookDto update(UpdateBookRequestDto requestDto, Long id, boolean areCategoriesReplaced);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters);
}
