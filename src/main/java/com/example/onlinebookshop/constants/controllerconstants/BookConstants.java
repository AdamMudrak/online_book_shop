package com.example.onlinebookshop.constants.controllerconstants;

public class BookConstants {
    public static final String BOOK_API_NAME = "Books API";
    public static final String BOOK_API_DESCRIPTION =
            "Here you'll find a comprehensive overview of how to create, read, update and delete "
                    + "books in this app.";

    public static final String GET_ALL_SUMMARY =
            "Get all books optionally with pagination and sorting";
    public static final String GET_ALL_DESCRIPTION = "Returns all books if not provided with"
            + " page number(page), page size(size) and sort parameter(sort)";

    public static final String GET_BY_ID_SUMMARY = "Get a book by id";
    public static final String GET_BY_ID_DESCRIPTION = "Returns a book as per the id";

    public static final String SEARCH_BOOKS_SUMMARY = "Search book by params";
    public static final String SEARCH_BOOKS_DESCRIPTION = "Returns a book with params sent if any";

    public static final String CREATE_BOOK_SUMMARY = "Create a new book";
    public static final String CREATE_BOOK_DESCRIPTION =
            "Creates a new book according to the entity provided";

    public static final String UPDATE_BOOK_SUMMARY = "Update an existing book";
    public static final String UPDATE_BOOK_DESCRIPTION =
            "Updates an existing book according to the entity provided";

    public static final String DELETE_BOOK_SUMMARY = "Delete an existing book";
    public static final String DELETE_BOOK_DESCRIPTION =
            "Deletes an existing book according to the id provided";

    public static final String VALID_ID_DESCRIPTION =
            "Book id, must exist in DB and be greater than 0";
    public static final String PAGEABLE_EXAMPLE = """
            {"page": 0,
            "size": 5,
             "sort": "price,DESC"}""";

    public static final String BOOLEAN = "areCategoriesReplaced";
    public static final String BOOLEAN_DESCRIPTION =
            "Choose if want to replace or add to existing categories list";
}
