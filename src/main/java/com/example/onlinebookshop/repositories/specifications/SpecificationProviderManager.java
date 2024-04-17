package com.example.onlinebookshop.repositories.specifications;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
