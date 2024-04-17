package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
