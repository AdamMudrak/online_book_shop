package com.example.onlinebookshop.repositories.book.spec;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.SpecificationProvider;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "author";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("author").in(Arrays
                .stream(params)
                .toArray()));
    }
}
