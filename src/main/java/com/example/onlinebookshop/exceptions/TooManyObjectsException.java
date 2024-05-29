package com.example.onlinebookshop.exceptions;

public class TooManyObjectsException extends RuntimeException {
    public TooManyObjectsException(String message) {
        super(message);
    }
}
