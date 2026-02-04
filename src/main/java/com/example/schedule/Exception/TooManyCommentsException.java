package com.example.schedule.Exception;

public class TooManyCommentsException extends RuntimeException {
    public TooManyCommentsException(String message) {
        super(message);
    }
}