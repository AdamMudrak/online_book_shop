package com.example.onlinebookshop.dto.book;

import com.example.onlinebookshop.dto.category.CategoryDto;
import java.math.BigDecimal;
import java.util.Set;

public record BookDto(Long id,
                      String title,
                      String author,
                      Set<CategoryDto> categories,
                      String isbn,
                      BigDecimal price,
                      String description,
                      String coverImage){}
