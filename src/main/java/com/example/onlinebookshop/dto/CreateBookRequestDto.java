package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @Size(min = 10, max = 13)
    private String isbn;
    @NotBlank
    @Min(value = 0)
    @Digits(integer = 19, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    @PathToFile
    private String description;
    private String coverImage;
}
