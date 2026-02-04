package com.example.schedule.dto.scheduleDto;

import com.example.schedule.entity.Comment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "스케줄 제목이 꼭 필요해요..")
    @Size(max = 30)
    private String name;
    @NotBlank(message = "스케줄 내용이 꼭 필요해요..")
    @Size(max = 200)
    private String contents;
    @NotBlank(message = "작성자가 꼭 필요해요..")
    private String writer;
    @NotBlank(message = "비밀번호가 꼭 필요해요..")
    private String pw;
}
