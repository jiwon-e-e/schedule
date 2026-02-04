package com.example.schedule.Exception;

public class NonExistentException extends RuntimeException {
    public NonExistentException(String message) {
        super(message);
    }
}
