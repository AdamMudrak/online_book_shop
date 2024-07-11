package com.example.onlinebookshop.repositories.book;

import com.example.onlinebookshop.entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllByCategoryId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.delete(book);
    }

    @Test
    void findBookByIsbn() {
    }

    @Test
    void existsByIsbn() {
    }
}
