package com.example.onlinebookshop.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    @Schema(name = "id", example = "1")
    private Long id;
    @Schema(name = "title", example = "Harry Potter")
    private String title;
    @Schema(name = "author", example = "J.K. Rowling")
    private String author;
    @Schema(name = "isbn", example = "1234567890")
    private String isbn;
    @Schema(name = "price", example = "999.99")
    @Min(value = 0)
    private BigDecimal price;
    @Schema(name = "description", example = "Any text up to 3000 chars including whitespaces")
    private String description;
    @Schema(name = "coverImage", example = "https://example.com/updated-cover-image.jpg")
    private String coverImage;
}
