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

import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookRequestDto {
    @Schema(name = TITLE, example = TITLE_EXAMPLE)
    private String title;
    @Schema(name = AUTHOR, example = AUTHOR_EXAMPLE)
    private String author;
    @Schema(name = ISBN, example = ISBN_EXAMPLE, description = ISBN_DESCRIPTION)
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = PRICE, example = PRICE_EXAMPLE, description = PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    @Schema(name = DESCRIPTION, example = DESCRIPTION_EXAMPLE)
    private String description;
    @Schema(name = COVER_IMAGE, example = COVER_IMAGE_EXAMPLE,
            description = COVER_IMAGE_DESCRIPTION)
    @PathToFile
    private String coverImage;
}
