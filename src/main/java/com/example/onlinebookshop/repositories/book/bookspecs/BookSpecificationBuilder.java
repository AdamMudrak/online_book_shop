package com.example.onlinebookshop.repositories.book.bookspecs;

import com.example.onlinebookshop.dto.book.request.BookSearchParametersDto;
import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.specifications.SpecificationBuilder;
import com.example.onlinebookshop.repositories.specifications.SpecificationProviderManager;
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
        if (bookSearchParametersDto.getTitles() != null
                && bookSearchParametersDto.getTitles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParametersDto.getTitles()));
        }
        if (bookSearchParametersDto.getAuthors() != null
                && bookSearchParametersDto.getAuthors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParametersDto.getAuthors()));
        }
        if (bookSearchParametersDto.getFromPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("fromPrice")
                    .getSpecification(new String[]{String
                            .valueOf(bookSearchParametersDto.getFromPrice())}));
        }
        if (bookSearchParametersDto.getToPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("toPrice")
                    .getSpecification(new String[]{String
                            .valueOf(bookSearchParametersDto.getToPrice())}));
        }
        return specification;
    }
}
