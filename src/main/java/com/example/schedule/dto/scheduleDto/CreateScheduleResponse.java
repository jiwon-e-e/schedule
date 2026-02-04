package com.example.schedule.dto.scheduleDto;



import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {
    private final Long id;
    private final String name;
    private final String contents;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateScheduleResponse(Long id, String name, String contents, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
