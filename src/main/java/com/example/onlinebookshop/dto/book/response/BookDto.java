package com.example.onlinebookshop.dto.book.response;

import java.math.BigDecimal;
import java.util.Set;

public record BookDto(Long id,
                      String title,
                      String author,
                      Set<Long> categoryIds,
                      String isbn,
                      BigDecimal price,
                      String description,
                      String coverImage){}
