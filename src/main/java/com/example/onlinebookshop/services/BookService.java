package com.example.onlinebookshop.services;

import com.example.onlinebookshop.entities.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
