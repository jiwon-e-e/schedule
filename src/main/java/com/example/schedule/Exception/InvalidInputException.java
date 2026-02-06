package com.example.schedule.Exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends SystemException {
    public InvalidInputException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
