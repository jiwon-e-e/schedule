package com.example.schedule.dto.scheduleDto;

import com.example.schedule.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateScheduleRequest {
    private String name;
    private String contents;
    private String writer;
    private String pw;
}
