package com.example.onlinebookshop.repositories.book;

import static com.example.onlinebookshop.TestConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.TestConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.TestConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.TestConstants.PATH_TO_SQL_SCRIPTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import java.math.BigDecimal;
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
    private static final String TEST_ISBN_1 = "9780743273565";
    private static final String TEST_ISBN_2 = "9780061120084";
    private static final String TEST_ISBN_3 = "9780451524935";
    private static final String NON_EXISTING_ISBN = "0000000000000";
    private static final long NON_EXISTING_CATEGORY_ID = 1000L;
    private static final String[] TEST_ISBNS = new String[]{TEST_ISBN_1, TEST_ISBN_2, TEST_ISBN_3};

    private static final Category CATEGORY = new Category();
    private static final long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";

    private static final Book EXPECTED_BOOK_1 = new Book();
    private static final Book EXPECTED_BOOK_2 = new Book();
    private static final Book EXPECTED_BOOK_3 = new Book();

    private static final long GATSBY_ID = 1L;
    private static final String GATSBY_TITLE = "The Great Gatsby";
    private static final String GATSBY_AUTHOR = "F. Scott Fitzgerald";
    private static final String GATSBY_ISBN = "9780743273565";
    private static final BigDecimal GATSBY_PRICE = BigDecimal.valueOf(12.99);
    private static final String GATSBY_DESCRIPTION = "A story of the fabulously wealthy Jay Gatsby "
            + "and his love for the beautiful Daisy Buchanan.";
    private static final String GATSBY_COVER_IMAGE = "https://example.com/gatsby.jpg";

    private static final long TKAM_ID = 2L;
    private static final String TKAM_TITLE = "To Kill a Mockingbird";
    private static final String TKAM_AUTHOR = "Harper Lee";
    private static final String TKAM_ISBN = "9780061120084";
    private static final BigDecimal TKAM_PRICE = BigDecimal.valueOf(10.49);
    private static final String TKAM_DESCRIPTION = "A novel that explores the irrationality of "
            + "adult attitudes towards race and class in the Deep South of the 1930s.";
    private static final String TKAM_COVER_IMAGE = "https://example.com/mockingbird.jpg";

    private static final long ID_1984 = 3L;
    private static final String TITLE_1984 = "1984";
    private static final String AUTHOR_1984 = "George Orwell";
    private static final String ISBN_1984 = "9780451524935";
    private static final BigDecimal PRICE_1984 = BigDecimal.valueOf(9.99);
    private static final String DESCRIPTION_1984 = "A dystopian novel set in Airstrip One, "
            + "a province of the superstate Oceania, whose residents are victims of perpetual war, "
            + "omnipresent government surveillance, and public manipulation.";
    private static final String COVER_IMAGE_1984 = "https://example.com/1984.jpg";

    private static final Book[] EXPECTED_BOOKS =
            new Book[]{EXPECTED_BOOK_1, EXPECTED_BOOK_2, EXPECTED_BOOK_3};

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void initVars() {
        CATEGORY.setId(CATEGORY_ID);
        CATEGORY.setName(CATEGORY_NAME);
        CATEGORY.setDescription(CATEGORY_DESCRIPTION);

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
        assertTrue(bookRepository.findAllByCategoryId(NON_EXISTING_CATEGORY_ID).isEmpty());
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given an ISBN of 3 present books, successfully retrieve each of them by ISBN")
    @Test
    void findBookByIsbn_IsAbleToGetBooksByExistingIsbn_Success() {
        Book actualBook = new Book();
        if (bookRepository.findBookByIsbn(TEST_ISBN_1).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(TEST_ISBN_1).get();
        }
        assertEquals(EXPECTED_BOOK_1, actualBook);

        if (bookRepository.findBookByIsbn(TEST_ISBN_2).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(TEST_ISBN_2).get();
        }
        assertEquals(EXPECTED_BOOK_2, actualBook);

        if (bookRepository.findBookByIsbn(TEST_ISBN_3).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(TEST_ISBN_3).get();
        }
        assertEquals(EXPECTED_BOOK_3, actualBook);
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Given not a real ISBN, retrieve nothing")
    @Test
    void findBookByIsbn_IsNotAbleToGetBookByNonExistingIsbn_Fail() {
        assertTrue(bookRepository.findBookByIsbn(NON_EXISTING_ISBN).isEmpty());
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
