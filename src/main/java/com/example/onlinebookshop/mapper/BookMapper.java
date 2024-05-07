package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.book.BookDto;
import com.example.onlinebookshop.dto.book.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.UpdateBookRequestDto;
import com.example.onlinebookshop.entities.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toCreateModel(CreateBookRequestDto requestDto);

    Book toUpdateModel(UpdateBookRequestDto requestDto);
}
