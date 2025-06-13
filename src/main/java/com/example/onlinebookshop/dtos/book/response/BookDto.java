package com.example.onlinebookshop.dtos.book.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Set<Long> categoryIds = new HashSet<>();
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
