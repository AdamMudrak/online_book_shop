package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.BookRepository;
import com.example.onlinebookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
