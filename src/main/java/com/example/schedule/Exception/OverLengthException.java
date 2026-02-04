package com.example.schedule.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OverLengthException extends ResponseStatusException {
    public OverLengthException() {
        super(HttpStatus.BAD_REQUEST, "문자열 길이 초과");

        System.out.println("문자열 길이 초과");
    }
}
