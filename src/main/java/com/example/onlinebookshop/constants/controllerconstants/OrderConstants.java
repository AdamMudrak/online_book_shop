package com.example.onlinebookshop.constants.controllerconstants;

public class OrderConstants {
    public static final String ORDER_API_NAME = "Orders API";
    public static final String ORDER_API_DESCRIPTION =
            "Here you'll find a comprehensive overview of how to add, watch and update "
                    + "orders in this app.";
    public static final String GET_ALL_SUMMARY =
            "Get all orders";

    public static final String ADD_ORDER_SUMMARY = "Add a new order";
    public static final String ADD_ORDER_DESCRIPTION =
            "Adds a new order using items from the shopping cart, clears the shopping cart";
    public static final String SHIPPING_ADDRESS_DESCRIPTION = """
            If not provided, the address from
            the user's account will be used.
            If there is there is nothing
            in the user's account as well,
            an error occurs, but you are able
            to try again.""";

    public static final String UPDATE_ORDER_SUMMARY = "Update the existing order status";

    public static final String GET_ORDER_ITEMS_BY_ORDER_ID = "Get items by specific order id";

    public static final String GET_ORDER_ITEM_BY_ORDER_ID_AND_ITEM_ID =
            "Get an item by specific order id and item id";

    public static final String ORDER_ID = "orderId";
    public static final String ITEM_ID = "itemId";
    public static final String VALID_ID_DESCRIPTION =
            "Order id, must exist in DB and be greater than 0";
    public static final String VALID_ITEM_ID_DESCRIPTION =
            "Order item id, must exist in DB and be greater than 0";
}
