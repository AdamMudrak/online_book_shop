package com.example.online_book_shop.exceptions;

public class DataProcessingException extends RuntimeException {
  public DataProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
