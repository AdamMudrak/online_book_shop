package com.example.onlinebookshop.constants;

public class BookDtoConstants {
    public static final String ID = "id";
    public static final String ID_EXAMPLE = "1";

    public static final String TITLE = "title";
    public static final String TITLE_EXAMPLE = "Harry Potter";
    public static final String TITLE_DESCRIPTION =
            "Must be exactly equal to the title in DB. You can specify many titles";

    public static final String AUTHOR = "author";
    public static final String AUTHOR_EXAMPLE = "J.K. Rowling";
    public static final String AUTHOR_DESCRIPTION =
            "Must be exactly equal to the author in DB. You can specify many authors";

    public static final String ISBN = "isbn";
    public static final String ISBN_EXAMPLE = "1234567890";
    public static final String ISBN_DESCRIPTION = "Length must be at least 10";

    public static final String PRICE = "price";
    public static final String PRICE_EXAMPLE = "999.99";
    public static final String PRICE_DESCRIPTION =
            "Price must contain 17 integers and 2 fractions maximum";
    public static final String FLOOR_PRICE = "799.99";
    public static final String FLOOR_PRICE_DESCRIPTION = "Defines floor price";
    public static final String CEILING_PRICE = "1999.99";
    public static final String CEILING_PRICE_DESCRIPTION =
            "Defines ceiling price. Must always be greater than floor price";

    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_EXAMPLE =
            "Any text up to 3000 chars including whitespaces";

    public static final String COVER_IMAGE = "coverImage";
    public static final String COVER_IMAGE_EXAMPLE = "https://example.com/updated-cover-image.jpg";
    public static final String COVER_IMAGE_DESCRIPTION = "Should contain http or https protocols";
}
