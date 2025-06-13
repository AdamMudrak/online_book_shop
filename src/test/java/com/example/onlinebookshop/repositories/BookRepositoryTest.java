package com.example.onlinebookshop.repositories;

import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.AUTHOR_1984;
import static com.example.onlinebookshop.BookCategoryConstants.COVER_IMAGE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.BookCategoryConstants.DESCRIPTION_1984;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.FICTION_CATEGORY_NAME;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ID;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.GATSBY_TITLE;
import static com.example.onlinebookshop.BookCategoryConstants.ID_1984;
import static com.example.onlinebookshop.BookCategoryConstants.ISBN_1984;
import static com.example.onlinebookshop.BookCategoryConstants.NON_EXISTING_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.PATH_TO_SQL_SCRIPTS;
import static com.example.onlinebookshop.BookCategoryConstants.PRICE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.RANDOM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.TEST_ISBNS;
import static com.example.onlinebookshop.BookCategoryConstants.TITLE_1984;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_AUTHOR;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_COVER_IMAGE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_DESCRIPTION;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ID;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_ISBN;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_PRICE;
import static com.example.onlinebookshop.BookCategoryConstants.TKAM_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final Category CATEGORY = new Category();
    private static final Book EXPECTED_BOOK_1 = new Book();
    private static final Book EXPECTED_BOOK_2 = new Book();
    private static final Book EXPECTED_BOOK_3 = new Book();
    private static final Book[] EXPECTED_BOOKS =
            new Book[]{EXPECTED_BOOK_1, EXPECTED_BOOK_2, EXPECTED_BOOK_3};
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void initVars() {
        CATEGORY.setId(FICTION_CATEGORY_ID);
        CATEGORY.setName(FICTION_CATEGORY_NAME);
        CATEGORY.setDescription(FICTION_CATEGORY_DESCRIPTION);

        EXPECTED_BOOK_1.setId(GATSBY_ID);
        EXPECTED_BOOK_1.setTitle(GATSBY_TITLE);
        EXPECTED_BOOK_1.setAuthor(GATSBY_AUTHOR);
        EXPECTED_BOOK_1.setIsbn(GATSBY_ISBN);
        EXPECTED_BOOK_1.setPrice(GATSBY_PRICE);
        EXPECTED_BOOK_1.setDescription(GATSBY_DESCRIPTION);
        EXPECTED_BOOK_1.setCoverImage(GATSBY_COVER_IMAGE);

        EXPECTED_BOOK_2.setId(TKAM_ID);
        EXPECTED_BOOK_2.setTitle(TKAM_TITLE);
        EXPECTED_BOOK_2.setAuthor(TKAM_AUTHOR);
        EXPECTED_BOOK_2.setIsbn(TKAM_ISBN);
        EXPECTED_BOOK_2.setPrice(TKAM_PRICE);
        EXPECTED_BOOK_2.setDescription(TKAM_DESCRIPTION);
        EXPECTED_BOOK_2.setCoverImage(TKAM_COVER_IMAGE);

        EXPECTED_BOOK_3.setId(ID_1984);
        EXPECTED_BOOK_3.setTitle(TITLE_1984);
        EXPECTED_BOOK_3.setAuthor(AUTHOR_1984);
        EXPECTED_BOOK_3.setIsbn(ISBN_1984);
        EXPECTED_BOOK_3.setPrice(PRICE_1984);
        EXPECTED_BOOK_3.setDescription(DESCRIPTION_1984);
        EXPECTED_BOOK_3.setCoverImage(COVER_IMAGE_1984);
    }

    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an id of an existing category, "
            + "successfully retrieve a list of three books")
    @Test
    void findAllByCategoryId_IsAbleToFindBooksByExistingCategoryId_Success() {
        int expectedListSize = 3;
        List<Book> actualBooks = bookRepository.findAllByCategoryId(1L);
        assertEquals(expectedListSize, actualBooks.size());
        for (Book expectedBook : EXPECTED_BOOKS) {
            expectedBook.setCategories(Set.of(CATEGORY));
        }
        actualBooks.forEach(book -> {
            int currentId = book.getId().intValue();
            assertEquals(EXPECTED_BOOKS[currentId - 1], book);
        });
        for (Book expectedBook : EXPECTED_BOOKS) {
            expectedBook.setCategories(Set.of());
        }
    }

    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an id of a non-existing category, retrieve an empty list")
    @Test
    void findAllByCategoryId_IsNotAbleToFindBooksByNonExistingCategoryId_Fail() {
        assertTrue(bookRepository.findAllByCategoryId(RANDOM_ID).isEmpty());
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an ISBN of 3 present books, successfully make sure each of them exists")
    @Test
    void existsByIsbn_IsAbleToFindAddedBooksWithExistingIsbn_Success() {
        for (String isbn : TEST_ISBNS) {
            assertTrue(bookRepository.existsByIsbn(isbn));
        }
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given not a real ISBN, "
            + "successfully make sure a book with such ISBN doesn't exist")
    @Test
    void existsByIsbn_IsNotAbleToFindBookWithNonExistingIsbn_Fail() {
        assertFalse(bookRepository.existsByIsbn(NON_EXISTING_ISBN));
    }
}
