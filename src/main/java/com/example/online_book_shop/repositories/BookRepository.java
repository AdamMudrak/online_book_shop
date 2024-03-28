package com.example.online_book_shop.repositories;

import com.example.online_book_shop.entities.Book;
import java.util.List;

public interface BookRepository {
  Book save(Book book);
  List<Book> findAll();
}
