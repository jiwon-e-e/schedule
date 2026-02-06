package com.example.schedule.Exception;

import org.springframework.http.HttpStatus;

public class TooManyCommentsException extends SystemException {
    public TooManyCommentsException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
