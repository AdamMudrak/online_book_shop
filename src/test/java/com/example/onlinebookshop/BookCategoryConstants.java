package com.example.onlinebookshop;

import java.math.BigDecimal;
import java.util.List;

public class BookCategoryConstants {
    /**Paths to sql files*/
    public static final String PATH_TO_SQL_SCRIPTS =
            "classpath:testdb/changelog/test-temporary-changes/repositories/";
    public static final String ADD_BOOKS_SQL = "add-books.sql";
    public static final String ADD_CATEGORIES_SQL = "add-categories.sql";
    public static final String ADD_BOOKS_CATEGORIES_SQL = "add-books-categories.sql";
    public static final String DELETE_BOOKS_SQL = "delete-books.sql";
    public static final String DELETE_CATEGORIES_SQL = "delete-categories.sql";
    public static final String DELETE_BOOKS_CATEGORIES_SQL = "delete-books-categories.sql";
    /**Test data for books and categories*/
    public static final long FIRST_CATEGORY_ID = 1L;
    public static final String NON_EXISTING_CATEGORY_NAME = "I don't even exist";
    public static final String CATEGORY_NAME = "Fiction";
    public static final String CATEGORY_DESCRIPTION =
            "Interesting books about imaginary though possible events";
    public static final String ANOTHER_CATEGORY_NAME = "Horror";
    public static final String UPDATED_CATEGORY_NAME = "NEW Horror";
    public static final String ANOTHER_CATEGORY_DESCRIPTION = "Horror description";
    public static final String UPDATED_CATEGORY_DESCRIPTION = "NEW Horror description";

    public static final long GATSBY_ID = 1L;
    public static final String GATSBY_TITLE = "The Great Gatsby";
    public static final String GATSBY_AUTHOR = "F. Scott Fitzgerald";
    public static final String GATSBY_ISBN = "9780743273565";
    public static final BigDecimal GATSBY_PRICE = BigDecimal.valueOf(12.99);
    public static final String GATSBY_DESCRIPTION = "A story of the fabulously wealthy Jay Gatsby "
            + "and his love for the beautiful Daisy Buchanan.";
    public static final String GATSBY_COVER_IMAGE = "https://example.com/gatsby.jpg";

    public static final long TKAM_ID = 2L;
    public static final String TKAM_TITLE = "To Kill a Mockingbird";
    public static final String TKAM_AUTHOR = "Harper Lee";
    public static final String TKAM_ISBN = "9780061120084";
    public static final BigDecimal TKAM_PRICE = BigDecimal.valueOf(10.49);
    public static final String TKAM_DESCRIPTION = "A novel that explores the irrationality of "
            + "adult attitudes towards race and class in the Deep South of the 1930s.";
    public static final String TKAM_COVER_IMAGE = "https://example.com/mockingbird.jpg";

    public static final long ID_1984 = 3L;
    public static final String TITLE_1984 = "1984";
    public static final String AUTHOR_1984 = "George Orwell";
    public static final String ISBN_1984 = "9780451524935";
    public static final BigDecimal PRICE_1984 = BigDecimal.valueOf(9.99);
    public static final String DESCRIPTION_1984 = "A dystopian novel set in Airstrip One, "
            + "a province of the superstate Oceania, whose residents are victims of perpetual war, "
            + "omnipresent government surveillance, and public manipulation.";
    public static final String COVER_IMAGE_1984 = "https://example.com/1984.jpg";

    public static final String SOME_TITLE = "Some book";
    public static final String SOME_AUTHOR = "Some author";
    public static final String SOME_ISBN = "0000000000000";
    public static final BigDecimal SOME_PRICE = BigDecimal.valueOf(9.99);
    public static final String SOME_DESCRIPTION = "Some description";
    public static final String SOME_COVER_IMAGE = "https://example.com/some_picture.jpg";
    /**ID*/
    public static final long NEW_CATEGORY_ID = 2L;
    public static final long DUPLICATE_OF_EXISTING_CATEGORY_ID = 3L;
    public static final long EXPECTED_BOOK_DTO_ID = 4L;
    public static final long RANDOM_ID = 1000L;
    /**ISBN*/
    public static final String[] TEST_ISBNS = new String[]{GATSBY_ISBN, TKAM_ISBN, ISBN_1984};
    public static final String NON_EXISTING_ISBN = "0000000000000";
    /**Data for connecting to Controller*/
    public static final String SPLITERATOR = "/";
    public static final String BOOKS_URL = "/books";
    public static final String CATEGORIES_URL = "/categories";
    public static final String BOOKS_SEARCH_URL = "/search";
    public static final String USER_NAME = "user";
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";
    /**Bad request 400 error messages*/
    public static final String INVALID_FOR_NOT_BLANK = "";
    public static final String INVALID_ISBN_TOO_SHORT = "123456789";
    public static final String VALID_ISBN = "1234567890";
    public static final long INVALID_CATEGORY_ID_DOES_NOT_EXIST = 2L;
    public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(1);
    public static final BigDecimal INVALID_PRICE_NEGATIVE = BigDecimal.valueOf(-1);
    public static final int REPEAT_DESCRIPTION_COUNT = 100;
    public static final String INVALID_DESCRIPTION_TOO_LONG =
            "This text contains more than 3000 characters.".repeat(REPEAT_DESCRIPTION_COUNT);
    public static final String VALID_DESCRIPTION =
            "This text contains more than 0 and less than 3000 characters.";
    public static final String INVALID_COVER_IMAGE_WITHOUT_HTTPS = "example.com/gatsby.jpg";
    public static final String VALID_COVER_IMAGE = "https://example.com/another_gatsby.jpg";
    public static final int FLOOR_PRICE = 10;
    public static final int CEILING_PRICE = 12;
    public static final List<String> CREATE_ERRORS_LIST = List.of(
            "title must not be blank",
            "author must not be blank",
            "isbn size must be between 10 and 17",
            "price must be greater than 0",
            "description size must be between 0 and 3000",
            "coverImage Invalid format filepath"
    );
    public static final List<String> UPDATE_ERRORS_LIST = List.of(
            "isbn size must be between 10 and 17",
            "price must be greater than 0",
            "description size must be between 0 and 3000",
            "coverImage Invalid format filepath"
    );
    public static final String CATEGORY_DTO_ERROR = "name must not be blank";
    /**Pageable imitation data*/
    public static final int RANDOM_PAGE_NUMBER = 1000;
    public static final int FIRST_PAGE_NUMBER = 0;
    public static final int SECOND_PAGE_NUMBER = 1;
    public static final int UNLIMITED_PAGE_SIZE = 3;
    public static final int LIMITED_PAGE_SIZE = 2;
    public static final String SORT_BY_TITLE_DESC = "title,DESC";
    public static final String SORT_BY_PRICE_ASC = "price,ASC";
    public static final String SORT = "sort";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String TITLES = "titles";
    public static final String TITLES_LIST = "1984,To Kill a Mockingbird";
    public static final String INVALID_PARAMS = "?titls=1984,To Kill a Mockingbird&price=5";
    public static final int EXPECTED_SIZE = 2;
}
