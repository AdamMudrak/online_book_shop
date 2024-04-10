package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.BookDto;
import com.example.onlinebookshop.dto.CreateBookRequestDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.mapper.BookMapper;
import com.example.onlinebookshop.repositories.BookRepository;
import com.example.onlinebookshop.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

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
}
