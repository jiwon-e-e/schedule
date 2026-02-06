package com.example.schedule.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SystemException extends RuntimeException {
    private final HttpStatus status;

    public SystemException(HttpStatus status, String message) {
        super(message);
        this.status= status;
    }
}
