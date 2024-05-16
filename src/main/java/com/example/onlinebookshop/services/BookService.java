package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.request.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookRequestDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
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
