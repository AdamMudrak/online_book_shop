package com.example.onlinebookshop.dtos.book.request;

import java.math.BigDecimal;

public record BookSearchParametersDto(
        BigDecimal fromPrice,
        BigDecimal toPrice,
        String[] titles,
        String[] authors){}
