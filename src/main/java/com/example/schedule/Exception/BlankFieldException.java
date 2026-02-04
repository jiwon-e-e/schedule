package com.example.schedule.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BlankFieldException extends ResponseStatusException {
    public BlankFieldException() {

        super(HttpStatus.BAD_REQUEST, "필수값 누락");
        System.out.println("필수값 누락");
    }
}
