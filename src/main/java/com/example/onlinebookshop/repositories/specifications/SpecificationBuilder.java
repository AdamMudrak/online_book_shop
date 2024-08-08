package com.example.onlinebookshop.repositories.specifications;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto bookSearchParametersDto);
}
