package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Size(min = 10)
    private String isbn;
    @NotNull
    @Min(value = 0)
    @Digits(integer = 19, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    private String description;
    @PathToFile
    private String coverImage;
}
