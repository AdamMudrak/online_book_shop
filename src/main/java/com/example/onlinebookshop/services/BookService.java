package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.request.CreateBookDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
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
