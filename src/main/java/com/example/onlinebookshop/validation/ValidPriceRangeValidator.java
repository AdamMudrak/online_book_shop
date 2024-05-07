package com.example.onlinebookshop.validation;

import com.example.onlinebookshop.dto.book.BookSearchParametersDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPriceRangeValidator
        implements ConstraintValidator<ValidPriceRange, BookSearchParametersDto> {

    @Override
    public boolean isValid(BookSearchParametersDto searchParametersDto,
                           ConstraintValidatorContext context) {
        if (searchParametersDto.getFromPrice() == null
                || searchParametersDto.getToPrice() == null) {
            return true;
        }
        return searchParametersDto.getFromPrice().compareTo(searchParametersDto.getToPrice()) < 0;
    }
}
