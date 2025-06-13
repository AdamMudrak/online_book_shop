package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dtos.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.dtos.book.request.CreateBookDto;
import com.example.onlinebookshop.dtos.book.request.UpdateBookDto;
import com.example.onlinebookshop.dtos.book.response.BookDto;
import com.example.onlinebookshop.dtos.book.response.BookDtoWithoutCategoryIds;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ParameterAlreadyExistsException;
import com.example.onlinebookshop.mappers.BookMapper;
import com.example.onlinebookshop.repositories.BookRepository;
import com.example.onlinebookshop.repositories.CategoryRepository;
import com.example.onlinebookshop.repositories.book.bookspecs.BookSpecificationBuilder;
import com.example.onlinebookshop.services.BookService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookDto requestDto) {
        if (bookRepository.existsByIsbn(requestDto.getIsbn())) {
            throw new ParameterAlreadyExistsException("Another book with ISBN "
                    + requestDto.getIsbn() + " already exists in DB");
        }
        isCategoryIdPresentInDb(requestDto.getCategoryIds());
        Book book = bookMapper.toCreateModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookMapper.toDtoList(
                bookRepository.findAll(pageable).getContent());
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllWithoutCategoryIds(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return bookMapper.toDtoWithoutCategoriesList(
                    bookRepository.findAllByCategoryId(id));
        }
        throw new EntityNotFoundException("Can't find category by id " + id);
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)));
    }

    @Override
    public BookDto update(UpdateBookDto requestDto, Long id, boolean areCategoriesReplaced) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find and update book by id " + id));

        Optional<Long> bookIdByIsbn = bookRepository.findBookIdByIsbn(requestDto.getIsbn());
        if (bookIdByIsbn.isPresent()
                && bookIdByIsbn.get().equals(id)) {
            throw new ParameterAlreadyExistsException("Book with ISBN " + requestDto.getIsbn()
                    + " already exists in DB");
        }
        isCategoryIdPresentInDb(requestDto.getCategoryIds());
        if (!areCategoriesReplaced) {
            addIdsFromDtoToDbBook(requestDto, book);
        }
        book = bookMapper.toUpdateModel(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
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

    private void isCategoryIdPresentInDb(Set<Long> categoryIds) {
        categoryIds.forEach(id -> categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category by id " + id)));
    }

    private void addIdsFromDtoToDbBook(UpdateBookDto requestDto, Book book) {
        Set<Long> presentCategoriesIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        presentCategoriesIds.addAll(requestDto.getCategoryIds());
        requestDto.setCategoryIds(presentCategoriesIds);
    }
}
