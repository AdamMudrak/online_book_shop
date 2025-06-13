package com.example.onlinebookshop.constants.controllers;

public class CategoryControllerConstants {
    public static final String CATEGORY_API_NAME = "Categories API";
    public static final String CATEGORY_API_DESCRIPTION = "Here you'll find a comprehensive "
            + "overview of how to create, read, update and delete categories in this app.";

    public static final String GET_ALL_SUMMARY =
            "Get all categories optionally with pagination and sorting";
    public static final String GET_ALL_DESCRIPTION = "Returns all categories if not provided with"
            + " page number(page), page size(size) and sort parameter(sort)";

    public static final String GET_BY_ID_SUMMARY = "Get a category by id";
    public static final String GET_BY_ID_DESCRIPTION = "Returns a category as per the id";

    public static final String CREATE_CATEGORY_SUMMARY = "Create a new category";
    public static final String CREATE_CATEGORY_DESCRIPTION =
            "Creates a new category according to the entity provided";

    public static final String UPDATE_CATEGORY_SUMMARY = "Update an existing category";
    public static final String UPDATE_CATEGORY_DESCRIPTION =
            "Updates an existing category according to the entity provided";

    public static final String DELETE_CATEGORY_SUMMARY = "Delete an existing category";
    public static final String DELETE_CATEGORY_DESCRIPTION =
            "Deletes an existing category according to the id provided";

    public static final String VALID_ID_DESCRIPTION =
            "Category id, must exist in DB and be greater than 0";
    public static final String PAGEABLE_EXAMPLE = """
            {"page": 0,
            "size": 3,
             "sort": "name,DESC"}""";
}
