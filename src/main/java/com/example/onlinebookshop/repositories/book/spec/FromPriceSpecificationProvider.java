package com.example.onlinebookshop.repositories.book.spec;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.SpecificationProvider;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public class FromPriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "fromPrice";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        BigDecimal price = new BigDecimal(params[0]);
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.ge(root.get("price"), price));
    }
}
