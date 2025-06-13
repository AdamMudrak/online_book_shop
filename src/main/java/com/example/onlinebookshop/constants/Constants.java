package com.example.onlinebookshop.constants;

public class Constants {
    public static final String SUCCESSFULLY_RETRIEVED = "Successfully retrieved";
    public static final String SUCCESSFULLY_CREATED = "Successfully created";
    public static final String SUCCESSFULLY_UPDATED = "Successfully updated";
    public static final String SUCCESSFULLY_REGISTERED = "Successfully registered";
    public static final String SUCCESSFULLY_LOGGED_IN = "Successfully logged in";
    public static final String SUCCESSFULLY_ADDED = "Successfully added";

    public static final String INVALID_ID_DESCRIPTION =
            "Either a negative or non-existing id provided. API will tell which one";
    public static final String INVALID_ENTITY_VALUE =
            "One of the parameters is invalid. API will show which one";

    public static final String CODE_200 = "200";
    public static final String CODE_201 = "201";
    public static final String CODE_204 = "204";
    public static final String CODE_204_DESCRIPTION = "No content";
    public static final String CODE_400 = "400";

    public static final String ID = "id";
    public static final String ID_EXAMPLE = "1";

    public static final String SPACE = " ";
    public static final String SPLITERATOR = "=";

    public static final String AUTHORIZATION_REQUIRED = "Authorization required";

    public static final String CODE_401 = "401";

    public static final String GREEN = "\033[0;32m";// GREEN
    public static final String RESET = "\033[0m";//Reset

    public static final int FIRST_POSITION = 0;
    public static final int SECOND_POSITION = 1;
    public static final int THIRD_POSITION = 2;

    public static final String PASSWORD_DESCRIPTION = """
                    Your password should contain:
                    1) at least one lowercase letter, like 'a';
                    2) at least one uppercase letter, like 'A';
                    3) at least one number, like '0';
                    4) at least one special character, like '?!@#$%^&*~';
                    5) from 8 to 32 characters.""";
}
