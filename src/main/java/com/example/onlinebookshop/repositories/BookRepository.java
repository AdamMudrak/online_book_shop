package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
