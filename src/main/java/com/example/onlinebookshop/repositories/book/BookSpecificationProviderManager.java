package com.example.onlinebookshop.repositories.book;

import com.example.onlinebookshop.entities.Book;
import com.example.onlinebookshop.exceptions.SpecificationProviderNotFoundException;
import com.example.onlinebookshop.repositories.SpecificationProvider;
import com.example.onlinebookshop.repositories.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(bookSpecificationProvider -> bookSpecificationProvider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new SpecificationProviderNotFoundException(
                        "Can't find correct specification provider for key : " + key));
    }
}
