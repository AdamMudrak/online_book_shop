package com.example.onlinebookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearchParametersDto {
    private String title;
    private String author;
    private BigDecimal price;
}
