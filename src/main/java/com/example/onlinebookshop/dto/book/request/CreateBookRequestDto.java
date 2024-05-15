package com.example.onlinebookshop.dto.book.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.example.onlinebookshop.constants.dtoconstants.BookDtoConstants;
import com.example.onlinebookshop.dto.category.response.CategoryDto;
import com.example.onlinebookshop.validation.PathToFile;
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
public class CreateBookRequestDto {
    @Schema(name = BookDtoConstants.TITLE, example = BookDtoConstants.TITLE_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String title;
    @Schema(name = BookDtoConstants.AUTHOR, example = BookDtoConstants.AUTHOR_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private String author;
    @Schema(name = BookDtoConstants.CATEGORY, example = BookDtoConstants.CATEGORY_EXAMPLE,
            requiredMode = REQUIRED)
    @NotBlank
    private Set<CategoryDto> categories = new HashSet<>();
    @Schema(name = BookDtoConstants.ISBN, example = BookDtoConstants.ISBN_EXAMPLE,
            description = BookDtoConstants.ISBN_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = BookDtoConstants.PRICE, example = BookDtoConstants.PRICE_EXAMPLE,
            description = BookDtoConstants.PRICE_DESCRIPTION,
            requiredMode = REQUIRED)
    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Schema(name = BookDtoConstants.DESCRIPTION, example = BookDtoConstants.DESCRIPTION_EXAMPLE,
            requiredMode = REQUIRED)
    @Size(max = 3000)
    private String description;
    @Schema(name = BookDtoConstants.COVER_IMAGE, example = BookDtoConstants.COVER_IMAGE_EXAMPLE,
            description = BookDtoConstants.COVER_IMAGE_DESCRIPTION,
            requiredMode = REQUIRED)
    @PathToFile
    private String coverImage;
}
