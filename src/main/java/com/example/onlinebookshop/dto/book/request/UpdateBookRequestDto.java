package com.example.onlinebookshop.dto.book.request;

import com.example.onlinebookshop.constants.BookDtoConstants;
import com.example.onlinebookshop.dto.category.response.CategoryDto;
import com.example.onlinebookshop.validation.PathToFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookRequestDto {
    @Schema(name = BookDtoConstants.TITLE, example = BookDtoConstants.TITLE_EXAMPLE)
    private String title;
    @Schema(name = BookDtoConstants.AUTHOR, example = BookDtoConstants.AUTHOR_EXAMPLE)
    private String author;
    @Schema(name = BookDtoConstants.CATEGORY, example = BookDtoConstants.CATEGORY_EXAMPLE)
    private Set<CategoryDto> categories = new HashSet<>();
    @Schema(name = BookDtoConstants.ISBN, example = BookDtoConstants.ISBN_EXAMPLE,
            description = BookDtoConstants.ISBN_DESCRIPTION)
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = BookDtoConstants.PRICE, example = BookDtoConstants.PRICE_EXAMPLE,
            description = BookDtoConstants.PRICE_DESCRIPTION)
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Size(max = 3000)
    @Schema(name = BookDtoConstants.DESCRIPTION, example = BookDtoConstants.DESCRIPTION_EXAMPLE)
    private String description;
    @Schema(name = BookDtoConstants.COVER_IMAGE, example = BookDtoConstants.COVER_IMAGE_EXAMPLE,
            description = BookDtoConstants.COVER_IMAGE_DESCRIPTION)
    @PathToFile
    private String coverImage;
}
