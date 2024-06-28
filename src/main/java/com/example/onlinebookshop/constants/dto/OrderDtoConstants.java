package com.example.onlinebookshop.constants.dto;

public class OrderDtoConstants {
    public static final String STATUS_DTO = "status";
    public static final String STATUS_DTO_EXAMPLE = "CREATED";
    public static final String STATUS_DTO_RULES = """
            CREATED;
            PENDING_PAYMENT;
            IN_PROGRESS;
            SHIPPED;
            COMPLETED;
            CANCELLED;
            """;
}
