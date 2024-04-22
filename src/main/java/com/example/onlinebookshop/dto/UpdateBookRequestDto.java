package com.example.onlinebookshop.dto;

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookRequestDto {
    @Schema(name = "Book title", example = "Harry Potter")
    private String title;
    @Schema(name = "Book author", example = "J.K. Rowling")
    private String author;
    @Schema(name = "Book isbn", example = "1234567890", description = "Length must be at least 10")
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = "Book price", example = "999.99",
            description = "Price must contain 17 integers and 2 fractions maximum")
    @Min(value = 0)
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    @Schema(name = "Book description", example = "Any text up to 3000 chars including whitespaces")
    private String description;
    @Schema(name = "Book cover image path", example = "https://example.com/updated-cover-image.jpg",
            description = "Should contain http or https protocols")
    @PathToFile
    private String coverImage;
}
