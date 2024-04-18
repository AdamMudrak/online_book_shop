package com.example.onlinebookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookSearchParametersDto(String[] titles, String[] authors,
                                      String fromPrice, String toPrice) {
}
