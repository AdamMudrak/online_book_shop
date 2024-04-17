package com.example.onlinebookshop.repositories.book.bookspecs.bookfieldsspecs;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.repositories.specifications.SpecificationProvider;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ToPriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "toPrice";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        BigDecimal price = new BigDecimal(params[0]);
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.le(root.get("price"), price));
    }
}
