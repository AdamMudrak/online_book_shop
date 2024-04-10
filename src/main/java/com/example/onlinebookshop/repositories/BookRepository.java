package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}
