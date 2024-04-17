package com.example.onlinebookshop.repositories.book;

import com.example.onlinebookshop.dto.BookSearchParametersDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.SpecificationBuilder;
import com.example.onlinebookshop.repositories.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParametersDto.titles() != null
                && bookSearchParametersDto.titles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParametersDto.titles()));
        }
        if (bookSearchParametersDto.authors() != null
                && bookSearchParametersDto.authors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParametersDto.authors()));
        }
        if (bookSearchParametersDto.fromPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("fromPrice")
                    .getSpecification(new String[]{bookSearchParametersDto.fromPrice()}));
        }
        if (bookSearchParametersDto.toPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("toPrice")
                    .getSpecification(new String[]{bookSearchParametersDto.toPrice()}));
        }
        return specification;
    }
}
