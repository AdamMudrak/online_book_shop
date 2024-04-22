package com.example.onlinebookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private Long id;
    @Schema(name = "Book title", example = "Harry Potter")
    private String title;
    @Schema(name = "Book author", example = "J.K. Rowling")
    private String author;
    @Schema(name = "Book isbn", example = "1234567890")
    private String isbn;
    @Schema(name = "Book price", example = "999.99")
    @Min(value = 0)
    private BigDecimal price;
    @Schema(name = "Book description", example = "Any text up to 3000 chars including whitespaces")
    private String description;
    @Schema(name = "Book cover image path", example = "https://example.com/updated-cover-image.jpg")
    private String coverImage;
}
