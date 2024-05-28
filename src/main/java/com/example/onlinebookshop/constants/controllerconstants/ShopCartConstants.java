package com.example.onlinebookshop.constants.controllerconstants;

public class ShopCartConstants {
    public static final String SHOPPING_CART_API_NAME = "Shopping cart API";
    public static final String SHOPPING_CART_API_DESCRIPTION =
            "Here you'll find a comprehensive overview of how to add, delete, update and look at "
                    + "items in user's shopping cart";

    public static final String GET_ALL_SUMMARY =
            "Get all items in the shopping cart optionally with pagination and sorting";
    public static final String GET_ALL_DESCRIPTION = "Returns all items if not provided with"
            + " page number(page), page size(size) and sort parameter(sort)";

    public static final String PAGEABLE_EXAMPLE = """
            {"page": 0,
            "size": 5,
             "sort": "bookId,DESC"}""";

    public static final String ADD_ITEM_SUMMARY = "Add item to the shopping cart";
    public static final String DELETE_ITEM_SUMMARY = "Delete item from the shopping cart";
    public static final String UPDATE_ITEM_SUMMARY = "Update item in the shopping cart";

    public static final String BOOK_ID = "Book id";
    public static final String QUANTITY = "Quantity";
    public static final String QUANTITY_EXAMPLE = "25";

}
