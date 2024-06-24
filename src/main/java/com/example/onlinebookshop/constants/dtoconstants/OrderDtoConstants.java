package com.example.onlinebookshop.constants.dtoconstants;

public class OrderDtoConstants {
    public static final String STATUS_DTO = "status";
    public static final String STATUS_DTO_EXAMPLE = "1";
    public static final String STATUS_DTO_RULES = """
            1 -> mark order as CREATED;
            2 -> mark order as PENDING_PAYMENT;
            3 -> mark order as IN_PROGRESS;
            4 -> mark order as SHIPPED;
            5 -> mark order as COMPLETED;
            6 -> mark order as CANCELLED;
            
            Status code are from 1 to 6 and should be so,
            otherwise an error occurs, but you are able
            to try again.
            """;
}
