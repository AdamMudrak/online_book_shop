package com.example.onlinebookshop.dtos.book.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.example.onlinebookshop.validation.path.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookDto {
    @Schema(name = "title", example = "Harry Potter",
            requiredMode = REQUIRED)
    @NotBlank
    private String title;
    @Schema(name = "author", example = "J.K. Rowling",
            requiredMode = REQUIRED)
    @NotBlank
    private String author;
    @Schema(name = "categoryIds", example = "[1,2]",
            requiredMode = REQUIRED)
    private Set<Long> categoryIds = new HashSet<>();
    @Schema(name = "isbn", example = "1234567890",
            description = "Length must be at least 10",
            requiredMode = REQUIRED)
    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = "price", example = "999.99",
            description = "Price must contain 17 integers and 2 fractions maximum",
            requiredMode = REQUIRED)
    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Schema(name = "description", example = "Any text up to 3000 chars including whitespaces",
            requiredMode = REQUIRED)
    @Size(max = 3000)
    private String description;
    @Schema(name = "coverImage", example = "https://example.com/updated-cover-image.jpg",
            description = "Should contain http or https protocols",
            requiredMode = REQUIRED)
    @PathToFile
    private String coverImage;
}
