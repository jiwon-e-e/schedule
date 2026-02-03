package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    private String name;
    private String contents;
    private String writer;
    private String pw;
}
