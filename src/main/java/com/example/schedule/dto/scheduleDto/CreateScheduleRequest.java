package com.example.schedule.dto.scheduleDto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String name;
    private String contents;
    private String writer;
    private String pw;
}
