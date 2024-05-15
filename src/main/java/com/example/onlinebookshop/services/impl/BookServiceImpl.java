package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.book.BookDto;
import com.example.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.dto.book.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.UpdateBookRequestDto;
import com.example.onlinebookshop.dto.category.CategoryDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.mapper.CategoryMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.services.BookService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryMapper categoryMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toCreateModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
        .map(bookMapper::toDto)
        .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllWithoutCategoryIds(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto update(UpdateBookRequestDto requestDto, Long id, boolean areCategoriesReplaced) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book updatedBook;
            if (areCategoriesReplaced) {
                updatedBook = bookMapper.toUpdateModel(requestDto);
                updatedBook.setId(id);
                return bookMapper.toDto(bookRepository.save(updatedBook));
            }
            Set<CategoryDto> presentCategories = book
                    .get()
                    .getCategories()
                    .stream()
                    .map(categoryMapper::toCategoryDto)
                    .collect(Collectors.toSet());
            presentCategories.addAll(requestDto.getCategories());
            requestDto.setCategories(presentCategories);
            updatedBook = bookMapper.toUpdateModel(requestDto);
            updatedBook.setId(id);
            return bookMapper.toDto(bookRepository.save(updatedBook));
        }
        throw new EntityNotFoundException("Can't find and update book by id " + id);
    }

    @Override
    public void delete(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Can't find and delete book by id " + id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
