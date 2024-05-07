package com.example.onlinebookshop.dto.book;

import static com.example.onlinebookshop.constants.BookDtoConstants.AUTHOR;
import static com.example.onlinebookshop.constants.BookDtoConstants.AUTHOR_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.COVER_IMAGE;
import static com.example.onlinebookshop.constants.BookDtoConstants.COVER_IMAGE_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.COVER_IMAGE_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.DESCRIPTION_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.ISBN;
import static com.example.onlinebookshop.constants.BookDtoConstants.ISBN_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.ISBN_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.PRICE;
import static com.example.onlinebookshop.constants.BookDtoConstants.PRICE_DESCRIPTION;
import static com.example.onlinebookshop.constants.BookDtoConstants.PRICE_EXAMPLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.TITLE;
import static com.example.onlinebookshop.constants.BookDtoConstants.TITLE_EXAMPLE;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookRequestDto {
    @Schema(name = TITLE, example = TITLE_EXAMPLE, requiredMode = REQUIRED)
    @NotBlank
    private String title;
    @Schema(name = AUTHOR, example = AUTHOR_EXAMPLE, requiredMode = REQUIRED)
    @NotBlank
    private String author;
    @Schema(name = ISBN, example = ISBN_EXAMPLE, description = ISBN_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = PRICE, example = PRICE_EXAMPLE, description = PRICE_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Schema(name = DESCRIPTION, example = DESCRIPTION_EXAMPLE,
            requiredMode = REQUIRED)
    @Size(max = 3000)
    private String description;
    @Schema(name = COVER_IMAGE, example = COVER_IMAGE_EXAMPLE,
            description = COVER_IMAGE_DESCRIPTION,
            requiredMode = REQUIRED)
    @PathToFile
    private String coverImage;
}
