package com.example.onlinebookshop.repositories.book.spec;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

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
