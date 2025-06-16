package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.request.UpdateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.example.onlinebookshop.dtos.book.response.BookDtoWithoutCategoryIds;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllWithoutCategoryIds(Long id);

    BookDto findById(Long id);

    BookDto update(UpdateBookDto requestDto, Long id, boolean areCategoriesReplaced);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters);
}
