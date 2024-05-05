package com.example.onlinebookshop.dto.book;

import com.example.onlinebookshop.constants.BookDtoConstants;
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
    @Schema(name = BookDtoConstants.TITLE, example = BookDtoConstants.TITLE_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String title;
    @Schema(name = BookDtoConstants.AUTHOR, example = BookDtoConstants.AUTHOR_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String author;
    @Schema(name = BookDtoConstants.ISBN, example = BookDtoConstants.ISBN_EXAMPLE,
            description = BookDtoConstants.ISBN_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;
    @Schema(name = BookDtoConstants.PRICE, example = BookDtoConstants.PRICE_EXAMPLE,
            description = BookDtoConstants.PRICE_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal price;
    @Schema(name = BookDtoConstants.DESCRIPTION, example = BookDtoConstants.DESCRIPTION_EXAMPLE,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 3000)
    private String description;
    @Schema(name = BookDtoConstants.COVER_IMAGE, example = BookDtoConstants.COVER_IMAGE_EXAMPLE,
            description = BookDtoConstants.COVER_IMAGE_DESCRIPTION,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @PathToFile
    private String coverImage;
}
