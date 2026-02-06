package com.example.schedule.Exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends SystemException {
    public WrongPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다.");
    }
}
