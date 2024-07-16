package com.example.onlinebookshop.repositories.book;

import static com.example.onlinebookshop.repositories.RepoConstants.ADD_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.ADD_BOOKS_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.ADD_CATEGORIES_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.DELETE_BOOKS_CATEGORIES_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.DELETE_BOOKS_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.DELETE_CATEGORIES_SQL;
import static com.example.onlinebookshop.repositories.RepoConstants.PATH_TO_SQL_SCRIPTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.entities.Category;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final String testIsbn1 = "9780743273565";
    private static final String testIsbn2 = "9780061120084";
    private static final String testIsbn3 = "9780451524935";
    private static final String NON_EXISTING_ISBN = "0000000000000";
    private static final long NON_EXISTING_CATEGORY_ID = 1000L;
    private static final String[] testIsbns = new String[]{testIsbn1, testIsbn2, testIsbn3};

    private static Book expectedBook1;
    private static Book expectedBook2;
    private static Book expectedBook3;
    private static Book[] expectedBooks;

    private static Category category;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void initVars() {
        category = new Category();
        category.setId(1L);
        category.setName("Fiction");
        category.setDescription("Interesting books about imaginary though possible events");

        expectedBook1 = new Book();
        expectedBook1.setId(1L);
        expectedBook1.setTitle("The Great Gatsby");
        expectedBook1.setAuthor("F. Scott Fitzgerald");
        expectedBook1.setIsbn("9780743273565");
        expectedBook1.setPrice(BigDecimal.valueOf(12.99));
        expectedBook1.setDescription("A story of the fabulously wealthy Jay Gatsby and his love "
                + "for the beautiful Daisy Buchanan.");
        expectedBook1.setCoverImage("gatsby.jpg");

        expectedBook2 = new Book();
        expectedBook2.setId(2L);
        expectedBook2.setTitle("To Kill a Mockingbird");
        expectedBook2.setAuthor("Harper Lee");
        expectedBook2.setIsbn("9780061120084");
        expectedBook2.setPrice(BigDecimal.valueOf(10.49));
        expectedBook2.setDescription("A novel that explores the irrationality of adult attitudes "
                + "towards race and class in the Deep South of the 1930s.");
        expectedBook2.setCoverImage("mockingbird.jpg");

        expectedBook3 = new Book();
        expectedBook3.setId(3L);
        expectedBook3.setTitle("1984");
        expectedBook3.setAuthor("George Orwell");
        expectedBook3.setIsbn("9780451524935");
        expectedBook3.setPrice(BigDecimal.valueOf(9.99));
        expectedBook3.setDescription("A dystopian novel set in Airstrip One, a province of the "
                + "superstate Oceania, whose residents are victims of perpetual war, omnipresent "
                + "government surveillance, and public manipulation.");
        expectedBook3.setCoverImage("1984.jpg");

        expectedBooks = new Book[]{expectedBook1, expectedBook2, expectedBook3};
    }

    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + ADD_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_CATEGORIES_SQL,
            PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAllByCategoryId_IsAbleToFindBooksByExistingCategoryId_Success() {
        int expectedListSize = 3;
        List<Book> actualBooks = bookRepository.findAllByCategoryId(1L);
        assertEquals(expectedListSize, actualBooks.size());
        for (Book expectedBook : expectedBooks) {
            expectedBook.setCategories(Set.of(category));
        }
        actualBooks.forEach(book -> {
            int currentId = book.getId().intValue();
            assertEquals(expectedBooks[currentId - 1], book);
        });
        for (Book expectedBook : expectedBooks) {
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
    @Test
    void findAllByCategoryId_IsNotAbleToFindBooksByNonExistingCategoryId_Fail() {
        assertTrue(bookRepository.findAllByCategoryId(NON_EXISTING_CATEGORY_ID).isEmpty());
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findBookByIsbn_IsAbleToGetBooksByExistingIsbn_Success() {
        Book actualBook = new Book();
        if (bookRepository.findBookByIsbn(testIsbn1).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(testIsbn1).get();
        }
        assertEquals(expectedBook1, actualBook);

        if (bookRepository.findBookByIsbn(testIsbn2).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(testIsbn2).get();
        }
        assertEquals(expectedBook2, actualBook);

        if (bookRepository.findBookByIsbn(testIsbn3).isPresent()) {
            actualBook = bookRepository.findBookByIsbn(testIsbn3).get();
        }
        assertEquals(expectedBook3, actualBook);
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findBookByIsbn_IsNotAbleToGetBookByNonExistingIsbn_Fail() {
        assertTrue(bookRepository.findBookByIsbn(NON_EXISTING_ISBN).isEmpty());
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void existsByIsbn_IsAbleToFindAddedBooksWithExistingIsbn_Success() {
        for (String isbn : testIsbns) {
            assertTrue(bookRepository.existsByIsbn(isbn));
        }
    }

    @Sql(scripts = PATH_TO_SQL_SCRIPTS + ADD_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_TO_SQL_SCRIPTS + DELETE_BOOKS_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void existsByIsbn_IsNotAbleToFindBookWithNonExistingIsbn_Fail() {
        assertFalse(bookRepository.existsByIsbn(NON_EXISTING_ISBN));
    }
}
