package com.example.onlinebookshop.dto.book.response;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Set<Long> categoryIds;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
