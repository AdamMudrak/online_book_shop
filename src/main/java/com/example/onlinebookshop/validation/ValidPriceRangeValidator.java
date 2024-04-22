package com.example.onlinebookshop.validation;

import com.example.onlinebookshop.dto.BookSearchParametersDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPriceRangeValidator
        implements ConstraintValidator<ValidPriceRange, BookSearchParametersDto> {

    @Override
    public boolean isValid(BookSearchParametersDto searchParametersDto,
                           ConstraintValidatorContext context) {
        if (searchParametersDto.fromPrice() == null || searchParametersDto.toPrice() == null) {
            return true;
        }
        return searchParametersDto.fromPrice().compareTo(searchParametersDto.toPrice()) > 0;
    }
}
