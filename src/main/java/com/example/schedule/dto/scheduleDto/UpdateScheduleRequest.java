package com.example.schedule.dto.scheduleDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @Size(max = 30)
    @NotBlank(message = "스케줄 제목이 꼭 필요해요..")
    private String name;
    @NotBlank(message = "작성자가 꼭 필요해요..")
    private String writer;
    private String pw;
}
