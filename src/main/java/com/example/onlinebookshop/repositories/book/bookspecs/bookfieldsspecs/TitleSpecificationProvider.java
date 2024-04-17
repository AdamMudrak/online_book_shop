package com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.specifications.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("title").in(Arrays
                        .stream(params)
                        .toArray()));
    }
}
