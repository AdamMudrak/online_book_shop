package com.example.onlinebookshop.mappers;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.example.onlinebookshop.dtos.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import java.util.List;
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

    List<BookDto> toDtoList(List<Book> books);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    @Mapping(target = "categories", ignore = true)
    Book toCreateModel(CreateBookDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookDto bookDto) {
        Set<Category> categories = bookDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    List<BookDtoWithoutCategoryIds> toDtoWithoutCategoriesList(List<Book> books);
}
