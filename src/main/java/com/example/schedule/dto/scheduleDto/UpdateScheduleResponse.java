package com.example.schedule.dto.scheduleDto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String name;
    private final String writer;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long id, String name, String writer, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.writer = writer;
        this.modifiedAt = modifiedAt;
    }
}
