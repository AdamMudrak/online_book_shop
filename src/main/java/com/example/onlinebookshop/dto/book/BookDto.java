package com.example.onlinebookshop.dto.book;

import com.example.onlinebookshop.constants.BookDtoConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    @Schema(name = BookDtoConstants.ID, example = BookDtoConstants.ID_EXAMPLE)
    private Long id;
    @Schema(name = BookDtoConstants.TITLE, example = BookDtoConstants.TITLE_EXAMPLE)
    private String title;
    @Schema(name = BookDtoConstants.AUTHOR, example = BookDtoConstants.AUTHOR_EXAMPLE)
    private String author;
    @Schema(name = BookDtoConstants.ISBN, example = BookDtoConstants.ISBN_EXAMPLE)
    private String isbn;
    @Schema(name = BookDtoConstants.PRICE, example = BookDtoConstants.PRICE_EXAMPLE)
    @Min(value = 0)
    private BigDecimal price;
    @Schema(name = BookDtoConstants.DESCRIPTION, example = BookDtoConstants.DESCRIPTION_EXAMPLE)
    private String description;
    @Schema(name = BookDtoConstants.COVER_IMAGE, example = BookDtoConstants.COVER_IMAGE_EXAMPLE)
    private String coverImage;
}
