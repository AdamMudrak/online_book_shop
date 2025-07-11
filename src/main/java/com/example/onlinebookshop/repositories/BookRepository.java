package com.example.onlinebookshop.repositories;

import com.example.onlinebookshop.entities.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query(value = "SELECT book FROM Book book "
            + "INNER JOIN book.categories categories "
            + "WHERE categories.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);

    Optional<Long> findBookIdByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}
