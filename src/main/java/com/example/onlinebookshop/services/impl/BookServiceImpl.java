package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.BookSearchParametersDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.book.BookRepository;
import com.example.onlinebookshop.repositories.book.BookSpecificationBuilder;
import com.example.onlinebookshop.services.BookService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto update(CreateBookRequestDto requestDto, Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book updatedBook = bookMapper.toModel(requestDto);
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
