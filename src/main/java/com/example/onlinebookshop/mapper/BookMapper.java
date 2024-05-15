package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.book.request.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookRequestDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    Book toCreateModel(CreateBookRequestDto requestDto);

    Book toUpdateModel(UpdateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.categoryIds().addAll(categoryIds);
    }
}
