package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dto.book.request.CreateBookRequestDto;
import com.example.onlinebookshop.dto.book.request.UpdateBookRequestDto;
import com.example.onlinebookshop.dto.book.response.BookDto;
import com.example.onlinebookshop.dto.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.repositories.category.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        if (bookRepository.existsByIsbn(requestDto.getIsbn())) {
            throw new ParameterAlreadyExistsException("Another book with ISBN "
                    + requestDto.getIsbn() + " already exists in DB");
        }
        isCategoryIdPresentInDb(requestDto);
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
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return bookRepository.findAllByCategoryId(id).stream()
                    .map(bookMapper::toDtoWithoutCategories)
                    .toList();
        }
        throw new EntityNotFoundException("Can't find category by id " + id);
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
            Optional<Book> bookByIsbn = bookRepository.findBookByIsbn(requestDto.getIsbn());
            if (bookByIsbn.isPresent() && !bookByIsbn.get().getId().equals(id)) {
                throw new ParameterAlreadyExistsException("Book with ISBN " + requestDto.getIsbn()
                        + " already exists in DB");
            }
            isCategoryIdPresentInDb(requestDto);
            if (!areCategoriesReplaced) {
                addIdsFromDtoToDbBook(requestDto, book.get());
            }
            return saveToDbAndGetSavedResult(requestDto, id);
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

    private void isCategoryIdPresentInDb(CreateBookRequestDto requestDto) {
        requestDto.getCategoryIds().forEach(id -> categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category by id " + id)));
    }

    private void isCategoryIdPresentInDb(UpdateBookRequestDto requestDto) {
        requestDto.getCategoryIds().forEach(id -> categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category by id " + id)));
    }

    private void addIdsFromDtoToDbBook(UpdateBookRequestDto requestDto, Book book) {
        Set<Long> presentCategoriesIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        presentCategoriesIds.addAll(requestDto.getCategoryIds());
        requestDto.setCategoryIds(presentCategoriesIds);
    }

    private BookDto saveToDbAndGetSavedResult(UpdateBookRequestDto requestDto, Long id) {
        Book book = bookMapper.toUpdateModel(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }
}
