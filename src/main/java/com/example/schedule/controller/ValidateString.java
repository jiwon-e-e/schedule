package com.example.schedule.controller;

import com.example.schedule.Exception.BlankFieldException;
import com.example.schedule.Exception.OverLengthException;
import org.springframework.stereotype.Component;

@Component
public class ValidateString {
    void validateNullableAndLength(String str, int length) {
        if (str == null || str.isBlank()) {
            throw new BlankFieldException();
        }
        if (str.length() > length) {
            throw new OverLengthException();
        }
    }

    void validateNullable(String str){
        if (str == null || str.isBlank()) {
            throw new BlankFieldException();
        }
    }
}
