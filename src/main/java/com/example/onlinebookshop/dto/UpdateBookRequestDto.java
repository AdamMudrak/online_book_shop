package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookRequestDto {
    private String title;
    private String author;
    @Size(min = 10)
    private String isbn;
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    private String description;
    @PathToFile
    private String coverImage;
}
