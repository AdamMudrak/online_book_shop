package com.example.onlinebookshop.controller;

public class BookConstants {
    static final String BOOK_API_NAME = "Books API";
    static final String BOOK_API_DESCRIPTION =
            "Here you'll find a comprehensive overview of functions of this app.";
    static final String GET_ALL_SUMMARY = "Get all books optionally with pagination "
            + "and sorting";
    static final String GET_ALL_DESCRIPTION = "Returns all books if not provided with"
            + " page number(page), page size(size) and sort parameter(sort)";
    static final String GET_BY_ID_SUMMARY = "Get a book by id";
    static final String GET_BY_ID_DESCRIPTION = "Returns a book as per the id";
    static final String SEARCH_BOOKS_SUMMARY = "Search book by params";
    static final String SEARCH_BOOKS_DESCRIPTION = "Returns a book with params sent if any";
    static final String CREATE_BOOK_SUMMARY = "Create a new book";
    static final String CREATE_BOOK_DESCRIPTION = "Creates a new book according to the "
            + "entity provided";
    static final String UPDATE_BOOK_SUMMARY = "Update an existing book";
    static final String UPDATE_BOOK_DESCRIPTION = "Updates an existing book according "
            + "to the entity provided";
    static final String DELETE_BOOK_SUMMARY = "Delete an existing book";
    static final String DELETE_BOOK_DESCRIPTION = "Deletes an existing book according to"
            + " the id provided";
    static final String VALID_ID_DESCRIPTION = "Book id, must exist in DB and"
            + " be greater than 0";
    static final String PAGEABLE_EXAMPLE = """
            {"page": 0,
            "size": 5,
             "sort": "price,DESC"}""";
}
